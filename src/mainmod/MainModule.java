package mainmod;

import dao.InsuranceServiceImpl;

import myexceptions.PolicyNotFoundException;
import util.DBConnection;
import java.sql.Connection;
import java.util.Collection;
import java.util.Scanner;

import entity.Policy;

public class MainModule {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection c = null;

        try 
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Loaded driver.");
            c = DBConnection.getConnection("db.properties");
            if (c != null) 
                System.out.println("Connection established with database.");
            InsuranceServiceImpl insurance = new InsuranceServiceImpl(c);

            while (true) {
                System.out.println("\nINSURANCE MANAGEMENT SYSTEM MENU");
                System.out.println("1. create policy");
                System.out.println("2. get policy by ID");
                System.out.println("3. get all policies");
                System.out.println("4. update policy");
                System.out.println("5. delete policy");
                System.out.println("6. exit");
                System.out.print("Choose an option: ");

                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
      
                    case 1: //to create a policy
                        System.out.print("Enter Policy ID: ");
                        int pid = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter Policy Name: ");
                        String pname = scanner.nextLine();
                        System.out.print("Enter Policy Type: ");
                        String pdetails = scanner.nextLine();

                        Policy np = new Policy(pid, pname, pdetails);
                        boolean ic = insurance.createPolicy(np);
                        if(ic==true) {
                        	System.out.println("Policy created.");
                        }
                        else {
                        	System.out.println("Policy creation failed.");
                        }
                        break;

                    case 2: //to retrieve a policy
                        System.out.print("Enter Policy ID to get: ");
                        int gpid = scanner.nextInt();
                        try {
                            Policy p = insurance.getPolicy(gpid);
                            System.out.println("Policy ID: " + p.getPolicyId());
                            System.out.println("Policy Name: " + p.getPolicyName());
                            System.out.println("Policy Type: " + p.getPolicyType());
                        } catch (PolicyNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 3: //to get all policies
                        Collection<Policy> pol = insurance.getAllPolicies();
                        System.out.println("All Policies:");
                        for (Policy policy : pol) {
                            System.out.println("Policy ID is - " + policy.getPolicyId() + ", Name - " + policy.getPolicyName() + ", Type - " + policy.getPolicyType());
                        }
                        break;

                    case 4: //to update a policy
                        System.out.print("Enter Policy ID to be updated: ");
                        int upid = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter new Policy Name: ");
                        String upname = scanner.nextLine();
                        System.out.print("Enter new Policy Details: ");
                        String updetails = scanner.nextLine();

                        Policy up = new Policy(upid, upname, updetails);
                        try {
                            boolean iu = insurance.updatePolicy(up);
                            if(iu==true) {
                            	System.out.println("Policy updated.");
                            }
                            else {
                            	System.out.println("Policy updation failed.");
                            }
                        } catch (PolicyNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 5: //to delete a policy
                        System.out.print("Enter Policy ID to delete: ");
                        int dpid = scanner.nextInt();
                        try {
                            boolean id = insurance.deletePolicy(dpid);
                            if (id) {
                                System.out.println("Policy deleted successfully!");
                            } else {
                                System.out.println("Failed to delete policy. Please check the ID and try again!");
                            }
                        } catch (PolicyNotFoundException e) {
                            System.out.println("Error: " + e.getMessage());
                        }
                        break;

                    case 6:
                        System.out.println("Exiting the program.");
                        return;

                    default:
                        System.out.println("Invalid option, please try again.");
                }
                System.out.println();
            }
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                c.close();
                scanner.close();
            } 
            catch (Exception e) 
            {
                e.printStackTrace();
            }
        }
    }
}
