package techx.alunos.service;

import techx.alunos.dto.AlunoRequest;
import techx.alunos.dto.AlunoResponse;
import techx.alunos.model.Aluno;
import techx.alunos.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AlunoService {

    private final AlunoRepository repository;

    public AlunoService(AlunoRepository repository) {
        this.repository = repository;
    }

    public AlunoResponse create(AlunoRequest request) {
        Aluno aluno = new Aluno();
        aluno.setNome(request.getNome());
        aluno.setEmail(request.getEmail());
        aluno.setCurso(request.getCurso());

        Aluno salvo = repository.save(aluno);
        return toResponse(salvo);
    }

    // Retorna null quando o aluno não existe
    public AlunoResponse getById(Long id) {
        Optional<Aluno> optional = repository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();
            return toResponse(aluno);
        } else {
            return null;
        }
    }

    // Retorna true se atualizou, false se o ID não existe
    public boolean update(Long id, AlunoRequest request) {
        Optional<Aluno> optional = repository.findById(id);

        if (optional.isPresent()) {
            Aluno aluno = optional.get();
            aluno.setNome(request.getNome());
            aluno.setEmail(request.getEmail());
            aluno.setCurso(request.getCurso());
            repository.save(aluno);
            return true;
        } else {
            return false;
        }
    }

    // Retorna true se removeu, false se o ID não existe
    public boolean delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private AlunoResponse toResponse(Aluno aluno) {
        return new AlunoResponse(
                aluno.getId(),
                aluno.getNome(),
                aluno.getEmail(),
                aluno.getCurso());
    }
}