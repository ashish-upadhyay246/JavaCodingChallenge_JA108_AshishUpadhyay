package dao;

import myexceptions.PolicyNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;

import entity.Policy;

public class InsuranceServiceImpl implements IPolicyService {
    private Connection conn;
    public InsuranceServiceImpl(Connection conn) 
    {
        this.conn = conn;
    }

    //to create a policy
    @Override
    public boolean createPolicy(Policy policy) {
        String q = "insert into Policy (policyId, policyName, policyDetails) values (?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(q)) {
            ps.setInt(1, policy.getPolicyId());
            ps.setString(2, policy.getPolicyName());
            ps.setString(3, policy.getPolicyType());
            int r = ps.executeUpdate();
            return r > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //to get a policy
    @Override
    public Policy getPolicy(int policyId) throws PolicyNotFoundException {
        try {
            String q = "select * from Policy where policyId = ?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setInt(1, policyId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) 
            {
                return new Policy(rs.getInt("policyId"),rs.getString("policyName"),rs.getString("policyDetails"));
            } 
            else 
            {
                throw new PolicyNotFoundException("Policy with ID " + policyId + " not found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new PolicyNotFoundException("Error occurred while retrieving policy: " + e.getMessage());
        }
    }

    //to update a policy
    @Override
    public boolean updatePolicy(Policy policy) throws PolicyNotFoundException {
            String q = "update Policy set policyName = ?, policyDetails = ? where policyId = ?";
        try(PreparedStatement ps = conn.prepareStatement(q)){
            ps.setString(1, policy.getPolicyName());
            ps.setString(2, policy.getPolicyType());
            ps.setInt(3, policy.getPolicyId());
            int result = ps.executeUpdate();
            if (result>0) {
            	return true;
            }
            else {
            	throw new PolicyNotFoundException("Policy with ID: "+policy.getPolicyId()+" does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        return false;
        }
    }

    //to delete a policy
    @Override
    public boolean deletePolicy(int policyId) throws PolicyNotFoundException {
        try {
            getPolicy(policyId);
            
            String q = "delete from Policy where policyId = ?";
            PreparedStatement ps = conn.prepareStatement(q);
            ps.setInt(1, policyId);
            int result = ps.executeUpdate();
            if(result==1) {
            	return true;
            }
            else {
            	throw new PolicyNotFoundException("Policy with ID: "+policyId+" does not exist.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //to get all policies
    @Override
    public Collection<Policy> getAllPolicies() {
        Collection<Policy> pol = new ArrayList<>();
        try 
        {
            String q = "select * from Policy";
            PreparedStatement ps = conn.prepareStatement(q);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) 
            {
                Policy policy = new Policy(rs.getInt("policyId"),rs.getString("policyName"),rs.getString("policyDetails"));
                pol.add(policy);
            }
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        }
        return pol;
    }
}
