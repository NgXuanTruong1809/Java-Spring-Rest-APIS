package vn.hoidanit.jobhunter.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long>,
        JpaSpecificationExecutor<Permission> {
    Optional<Permission> findByApiPathAndMethodAndModule(String apiPath, String method, String module);

    List<Permission> findByIdIn(List<Long> listPermission);
}
