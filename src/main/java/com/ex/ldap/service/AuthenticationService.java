package com.hsbc.ldap.service;

import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.stereotype.Service;

import com.hsbc.ldap.dto.AuthenticateRequest;

@Service

public class AuthenticationService {

	@Autowired
	LdapTemplate ldapTemplate;
	
	@Value("${base}")
	private String base;
	private final static String GROUP1="Group1";
	private final static String GROUP2="Group2";
	
	public String authenticate (AuthenticateRequest authenticateRequest)
	{
		if (!authenticateUser(authenticateRequest.getUserName(), authenticateRequest.getPassword()))
		{
			throw new RuntimeException("Invalid login creditionals");
		}
		List<String> groups=getUserGroupsByUser(authenticateRequest.getUserName());
		return generateJWTToken(authenticateRequest.getUserName(),groups);
	}
	private boolean authenticateUser( String userName, String password) 
    {
        return ldapTemplate.authenticate(base, "(uid=" + userName + ")", password);
     }
    
    private List<String> getUserGroupsByUser(String userName)
    {
        return ldapTemplate.search(
                LdapQueryBuilder.query().where("ou").is(GROUP1)
                .and("ou").is(GROUP2)
                .and("uid").is(userName),
                new AttributesMapper<String>() {
                    public String mapFromAttributes(Attributes attrs)
                            throws NamingException {
                        return (String) attrs.get("ou").get();
                    }
                });
    }
    private String generateJWTToken(String userName,List<String> groups)
    {
    	return userName.concat(groups.toString());
    }
}
