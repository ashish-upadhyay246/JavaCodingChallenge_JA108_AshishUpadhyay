package dao;

import myexceptions.PolicyNotFoundException;


import java.util.*;
import entity.Policy;

public interface IPolicyService {
    boolean createPolicy(Policy policy);
    Policy getPolicy(int policyId) throws PolicyNotFoundException;
    Collection<Policy> getAllPolicies();
    boolean updatePolicy(Policy policy) throws PolicyNotFoundException;
    boolean deletePolicy(int policyId) throws PolicyNotFoundException;
}
