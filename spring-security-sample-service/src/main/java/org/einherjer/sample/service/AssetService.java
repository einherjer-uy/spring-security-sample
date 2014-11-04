package org.einherjer.sample.service;

import org.einherjer.sample.model.Asset;
import org.einherjer.sample.repository.AssetRepository;
import org.einherjer.sample.service.exception.AssetNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AssetService {
	
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(AssetService.class);
	
	@Autowired
	private AssetRepository repository;
	
	
	@Transactional(readOnly=true)
    @PreAuthorize("permitAll")
    public Iterable<Asset> findAll() {
    	return this.repository.findAll();
    }
    @Transactional(readOnly=true)
    @PreAuthorize("isAuthenticated")
    public Asset get(Long tenantId) {
    	return repository.getOne(tenantId);
    }
    @Transactional(readOnly=true)
    @PreAuthorize("isAuthenticated")
    public Asset findByCode(String code) {
    	return repository.findByCode(code);
    }
    
    /**
     * matches POST semantics (always attempt to create a new entity)
     */
    @Transactional(rollbackFor=Throwable.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Asset create(Asset data) {
    	return internalCreateOrUpdate(null, data);
    }
    
    /**
     * matches PUT semantics (create if it doesn't exist, update if exists)
     */
    @Transactional(rollbackFor=Throwable.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Asset createOrUpdate(Long id, Asset data) {
    	return internalCreateOrUpdate(id, data);
    }

    private Asset internalCreateOrUpdate(Long tenantId, Asset data) {
        Asset tenant = null;
        if (tenantId != null) {
        	tenant = this.get(tenantId);
        }
    	if (tenant != null) { //this will never happen in the case of a POST because tenantId=null so the above line will return null
        	tenant.set(data);
            return tenant;
        }
        else {
            return this.repository.save(data);
        }
    }
    
    @Transactional(rollbackFor=Throwable.class)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(Long tenantId) throws AssetNotFoundException {
    	Asset tenant = this.get(tenantId);
    	Authentication a = SecurityContextHolder.getContext().getAuthentication();
    	if (tenant == null) {
    		throw new AssetNotFoundException(tenantId);
    	}
    	this.repository.delete(tenant);
    }
	
}
