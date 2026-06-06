package com.biblioteca.api_biblioteca.service;

import com.biblioteca.api_biblioteca.dto.LocacaoRequestDTO;
import com.biblioteca.api_biblioteca.dto.LocacaoResponseDTO;
import com.biblioteca.api_biblioteca.enums.StatusLivro;
import com.biblioteca.api_biblioteca.exception.RecursoNaoEncontradoException;
import com.biblioteca.api_biblioteca.exception.RegraDeNegocioException;
import com.biblioteca.api_biblioteca.model.Locacao;
import com.biblioteca.api_biblioteca.model.Livro;
import com.biblioteca.api_biblioteca.model.Usuario;
import com.biblioteca.api_biblioteca.repository.LocacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class LocacaoService {

    @Autowired
    private LocacaoRepository repository;

    @Autowired
    private LivroService livroService;

    @Autowired
    private UsuarioService usuarioService;

    private LocacaoResponseDTO toDTO(Locacao e) {
        long diasEmAtraso = 0;
        if (!e.getDevolvido() && LocalDate.now().isAfter(e.getDataPrevistaDevolucao())) {
            diasEmAtraso = ChronoUnit.DAYS.between(e.getDataDevolucao(), LocalDate.now());
        }

        return new LocacaoResponseDTO(
                e.getId(),
                e.getLivro().getTitulo(),
                e.getUsuario().getNome(),
                e.getUsuario().getCpf(),
                e.getDataLocacao(),
                e.getDataPrevistaDevolucao(),
                e.getDataDevolucao(),
                e.getTaxaLocacao(),
                e.getTaxaAtraso(),
                e.getMultaTotal(),
                e.getEmAtraso(),
                e.getDevolvido(),
                diasEmAtraso
        );
    }

    public List<LocacaoResponseDTO> listarTodos() {
        return repository.findAll().stream().map(this::toDTO).toList();
    }

    public List<LocacaoResponseDTO> listarEmAberto() {
        return repository.findByDevolvidoFalse().stream().map(this::toDTO).toList();
    }

    public List<LocacaoResponseDTO> listarEmAtraso() {
        return repository.findByEmAtrasoTrue().stream().map(this::toDTO).toList();
    }

    public List<LocacaoResponseDTO> listarPorUsuario(Long usuarioId) {
        Usuario usuario = usuarioService.buscarPorId(usuarioId);
        return repository.findByUsuario(usuario).stream().map(this::toDTO).toList();
    }

    public LocacaoResponseDTO locar(LocacaoRequestDTO dto) {
        Livro livro = livroService.buscarPorId(dto.getLivroId());
        if (!livro.getStatus().permiteEmprestimo()) {
            throw new RegraDeNegocioException("Livro não disponível para locação. Status atual: " + livro.getStatus());
        }
        if (livro.getQuantidade() <= 0) {
            throw new RegraDeNegocioException("Não há exemplares disponíveis em estoque");
        }
        Usuario usuario = usuarioService.buscarPorId(dto.getUsuarioId());

        Locacao locacao = new Locacao();
        locacao.setLivro(livro);
        locacao.setUsuario(usuario);
        locacao.setDataLocacao(LocalDate.now());
        locacao.setDataPrevistaDevolucao(LocalDate.now().plusDays(dto.getPrazoDias()));
        locacao.setTaxaLocacao(dto.getTaxaLocacao());
        locacao.setTaxaAtraso(dto.getTaxaAtraso());
        locacao.setMultaTotal(dto.getTaxaLocacao());
        locacao.setEmAtraso(false);
        locacao.setDevolvido(false);

        livro.setQuantidade(livro.getQuantidade() - 1);
        if (livro.getQuantidade() == 0) {
            livro.setStatus(StatusLivro.LOCADO);
        }
        livroService.salvar(livro);

        return toDTO(repository.save(locacao));
    }

    public LocacaoResponseDTO devolver(Long locacaoId) {
        Locacao locacao = repository.findById(locacaoId).orElseThrow(() -> new RecursoNaoEncontradoException("Locação não encontrada com ID: " + locacaoId));
    if (locacao.getDevolvido()) {
        throw new RegraDeNegocioException("Esta locação já foi devolvida");
    }

    LocalDate hoje = LocalDate.now();
    locacao.setDataDevolucao(hoje);
    locacao.setDevolvido(true);

    boolean atrasado = hoje.isAfter(locacao.getDataPrevistaDevolucao());
    locacao.setEmAtraso(atrasado);

    if (atrasado) {
        long diasAtraso = ChronoUnit.DAYS.between(locacao.getDataPrevistaDevolucao(), hoje);
        java.math.BigDecimal multa = locacao.getTaxaLocacao().add(locacao.getTaxaAtraso().multiply(java.math.BigDecimal.valueOf(diasAtraso)));
        locacao.setMultaTotal(multa);
    }

    Livro livro = locacao.getLivro();
    livro.setQuantidade(livro.getQuantidade() + 1);
    livro.setStatus(StatusLivro.ESTOQUE);
    livroService.salvar(livro);

    return toDTO(repository.save(locacao));
    }

}
