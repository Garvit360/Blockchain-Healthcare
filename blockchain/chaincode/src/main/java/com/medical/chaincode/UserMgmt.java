package com.medical.chaincode;

import org.hyperledger.fabric.contract.Context;
import org.hyperledger.fabric.contract.ContractInterface;
import org.hyperledger.fabric.contract.annotation.Contract;
import org.hyperledger.fabric.contract.annotation.Default;
import org.hyperledger.fabric.contract.annotation.Info;
import org.hyperledger.fabric.contract.annotation.Transaction;
import org.hyperledger.fabric.shim.ChaincodeException;
import org.hyperledger.fabric.shim.ChaincodeStub;
import org.hyperledger.fabric.shim.ledger.KeyValue;
import org.hyperledger.fabric.shim.ledger.QueryResultsIterator;

import com.owlike.genson.Genson;

import java.util.ArrayList;
import java.util.List;

@Contract(
        name = "UserMgmt",
        info = @Info(
                title = "User Management",
                description = "Smart contract for managing users and their permissions",
                version = "0.0.1-SNAPSHOT"))
@Default
public class UserMgmt implements ContractInterface {

    private final Genson genson = new Genson();

    private enum UserMgmtErrors {
        USER_NOT_FOUND,
        USER_ALREADY_EXISTS,
        INVALID_ACCESS
    }

    /**
     * User class to store user details on the ledger
     */
    private static class User {
        private String id;
        private String role;
        private String publicKey;
        private String[] permissions;

        public User() {
        }

        public User(String id, String role, String publicKey, String[] permissions) {
            this.id = id;
            this.role = role;
            this.publicKey = publicKey;
            this.permissions = permissions;
        }

        public String getId() {
            return id;
        }

        public String getRole() {
            return role;
        }

        public String getPublicKey() {
            return publicKey;
        }

        public String[] getPermissions() {
            return permissions;
        }
    }

    /**
     * Initialize the ledger with default admin users
     *
     * @param ctx the transaction context
     */
    @Transaction
    public void initLedger(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();
        
        // Create admin user
        User adminUser = new User(
            "admin",
            "ADMIN",
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1D...",
            new String[] { "READ_ALL", "WRITE_ALL", "ADMIN" }
        );
        
        String adminUserJson = genson.serialize(adminUser);
        stub.putStringState("USER_" + adminUser.getId(), adminUserJson);
    }

    /**
     * Creates a new user on the ledger
     *
     * @param ctx the transaction context
     * @param id user id
     * @param role user role (ADMIN, DOCTOR, PATIENT)
     * @param publicKey user's public key for encryption
     * @param permissions array of permission strings
     * @return the created user
     */
    @Transaction
    public User createUser(final Context ctx, final String id, final String role,
                        final String publicKey, final String[] permissions) {
        ChaincodeStub stub = ctx.getStub();

        String userKey = "USER_" + id;
        
        // Check if user already exists
        String userJson = stub.getStringState(userKey);
        if (!userJson.isEmpty()) {
            String errorMessage = String.format("User %s already exists", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, UserMgmtErrors.USER_ALREADY_EXISTS.toString());
        }

        User user = new User(id, role, publicKey, permissions);
        userJson = genson.serialize(user);
        stub.putStringState(userKey, userJson);

        return user;
    }

    /**
     * Retrieves a user with a given ID from the ledger
     *
     * @param ctx the transaction context
     * @param id user id
     * @return the user found on the ledger
     */
    @Transaction
    public User readUser(final Context ctx, final String id) {
        ChaincodeStub stub = ctx.getStub();
        
        String userKey = "USER_" + id;
        String userJson = stub.getStringState(userKey);

        if (userJson.isEmpty()) {
            String errorMessage = String.format("User %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, UserMgmtErrors.USER_NOT_FOUND.toString());
        }

        User user = genson.deserialize(userJson, User.class);
        return user;
    }

    /**
     * Updates existing user on the ledger with new permissions
     *
     * @param ctx the transaction context
     * @param id user id to be updated
     * @param newPermissions new permission array
     * @return the updated user
     */
    @Transaction
    public User updateUserPermissions(final Context ctx, final String id, final String[] newPermissions) {
        ChaincodeStub stub = ctx.getStub();
        
        String userKey = "USER_" + id;
        String userJson = stub.getStringState(userKey);

        if (userJson.isEmpty()) {
            String errorMessage = String.format("User %s does not exist", id);
            System.out.println(errorMessage);
            throw new ChaincodeException(errorMessage, UserMgmtErrors.USER_NOT_FOUND.toString());
        }

        User user = genson.deserialize(userJson, User.class);
        
        // Create updated user with new permissions
        User updatedUser = new User(
            user.getId(), 
            user.getRole(), 
            user.getPublicKey(), 
            newPermissions
        );
        
        // Update state in ledger
        String updatedUserJson = genson.serialize(updatedUser);
        stub.putStringState(userKey, updatedUserJson);
        
        return updatedUser;
    }

    /**
     * Gets all users from the ledger
     *
     * @param ctx the transaction context
     * @return array of all users found on the ledger
     */
    @Transaction
    public User[] getAllUsers(final Context ctx) {
        ChaincodeStub stub = ctx.getStub();

        List<User> userList = new ArrayList<>();
        
        // Get all users with prefix "USER_"
        QueryResultsIterator<KeyValue> results = stub.getStateByRange("USER_", "USER_~");

        for (KeyValue result : results) {
            User user = genson.deserialize(result.getStringValue(), User.class);
            userList.add(user);
        }

        User[] users = userList.toArray(new User[userList.size()]);
        return users;
    }
} 