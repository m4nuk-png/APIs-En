package techx.alunos.service;

import techx.alunos.dto.AlunoRequest;
import techx.alunos.dto.AlunoResponse;
import techx.alunos.exception.AlunoNaoEncontradoException;
import techx.alunos.model.Aluno;
import techx.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public AlunoResponse create(AlunoRequest request) {
        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setCurso(request.getCurso());

        Aluno salvo = alunoRepository.save(aluno);
        return toResponse(salvo);
    }

    public AlunoResponse getById(Long id) {
        Optional<Aluno> optional = alunoRepository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();
            return toResponse(aluno);
        } else {
            throw new AlunoNaoEncontradoException(id);
        }
    }

    // PUT: orElseThrow resolve o Optional e lança a exceção se o ID não existir
    public void update(Long id, AlunoRequest request) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setCurso(request.getCurso());

        alunoRepository.save(aluno);
    }

    // DELETE: existsById + if (!exists), sem lambda
    public void deleteById(Long id) {
        boolean exists = alunoRepository.existsById(id);

        if (!exists) {
            throw new AlunoNaoEncontradoException(id);
        }

        alunoRepository.deleteById(id);
    }

    private AlunoResponse toResponse(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCurso());
    }
}
