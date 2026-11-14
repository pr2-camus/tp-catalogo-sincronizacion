package ar.edu.um.prog2.camus.catalogo.sync.application.usecases;

import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlArrancarUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarAlDiaUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.in.SincronizarCatalogoUseCase;
import ar.edu.um.prog2.camus.catalogo.sync.domain.ports.out.VersionRepositoryPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SincronizarAlArrancarUseCaseImpl implements SincronizarAlArrancarUseCase {

    private static final Logger log = LoggerFactory.getLogger(SincronizarAlArrancarUseCaseImpl.class);

    private final VersionRepositoryPort versionRepository;
    private final SincronizarCatalogoUseCase sincronizarCompletoUseCase;
    private final SincronizarAlDiaUseCase sincronizarAlDiaUseCase;

    public SincronizarAlArrancarUseCaseImpl(
        VersionRepositoryPort versionRepository,
        SincronizarCatalogoUseCase sincronizarCompletoUseCase,
        SincronizarAlDiaUseCase sincronizarAlDiaUseCase
    ) {
        this.versionRepository = versionRepository;
        this.sincronizarCompletoUseCase = sincronizarCompletoUseCase;
        this.sincronizarAlDiaUseCase = sincronizarAlDiaUseCase;
    }

    @Override
    public void sincronizarAlArrancar() {
        Long versionLocal = versionRepository.obtenerVersionLocal();
        if (versionLocal == null) {
            log.info("Base vacia, ejecutando sincronizacion completa inicial...");
            sincronizarCompletoUseCase.sincronizarCompleto();
        } else {
            log.info("Version local: {}. Sincronizando al dia...", versionLocal);
            sincronizarAlDiaUseCase.sincronizarAlDia();
        }
    }
}
