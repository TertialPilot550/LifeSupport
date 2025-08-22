package sammc.lifeSupport.flexibleDatabaseServer.looselyTypedData;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author sammc
 */
@Repository
public interface LooselyTypedDataRepository extends JpaRepository<LooselyTypedData, Integer> {
}
