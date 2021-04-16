package ru.aisa.test_company.restservice.service;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.PrincipalCollection;
import ru.aisa.test_company.restservice.model.Role;
import ru.aisa.test_company.restservice.model.User;
import ru.aisa.test_company.restservice.repository.UserRepository;

import javax.annotation.Resource;

public class UserRealm extends JdbcRealm {

    @Resource
    private UserRepository userRepository;


    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        final UsernamePasswordToken authToken = (UsernamePasswordToken) token;
        User user = userRepository.findByUsername(authToken.getUsername());
        if(user == null){
            return null;
        }

        final SimpleAuthenticationInfo authInfo = new SimpleAuthenticationInfo(
                authToken.getUsername(),
                authToken.getPassword(),
                getName());

        return authInfo;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals.fromRealm(getName()).isEmpty())
            return null;

        final String username = (String) principals.fromRealm(getName()).iterator().next();
        final User user = userRepository.findByUsername(username);

        if (user == null)
            return null;

        final SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        for (Role role : user.getRoles())
            authorizationInfo.addRole(role.toString());

        return authorizationInfo;
    }
}
