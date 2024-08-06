package org.example.authservice.repository;
import jakarta.transaction.Transactional;
import org.example.authservice.entity.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AccountRepository extends CrudRepository<Account, UUID> {
    @Transactional
    Account findByUsername(String username);
    Account findByAccountUuid(UUID accountUuid);
    Optional<Account> findByUsernameOrEmail(String username, String email);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
