package be.kdg.kandoe.backend.service.api;

import be.kdg.kandoe.backend.model.organizations.Organization;
import be.kdg.kandoe.backend.model.users.User;
import be.kdg.kandoe.backend.service.exceptions.EmailServiceException;

import java.util.List;

/**
 * Created by Wannes on 29/02/16.
 */
public interface EmailService {
    void inviteUnexistingUsersToOrganization(Organization organization, User requester, List<String> emails) throws EmailServiceException;
    void inviteUsersToOrganization(Organization organization, User requester, List<User> users) throws EmailServiceException;
}