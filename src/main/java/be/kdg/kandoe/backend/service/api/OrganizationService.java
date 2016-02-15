package be.kdg.kandoe.backend.service.api;

import be.kdg.kandoe.backend.model.organizations.Organization;
import be.kdg.kandoe.backend.service.exceptions.OrganizationServiceException;

import java.util.List;

/**
 * Created by Wannes on 2/11/2016.
 */
public interface OrganizationService {
    Organization addOrganization(Organization organization) throws OrganizationServiceException;
    Organization getOrganizationById(int id) throws OrganizationServiceException;
    Organization getOrganizationByName(String name) throws OrganizationServiceException;
    List<Organization> getOrganizationsByOwner(String owner) throws OrganizationServiceException;
}
