package com.medical.blockchain.config;

import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.hyperledger.fabric.gateway.Wallet;
import org.hyperledger.fabric.gateway.Wallets;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class BlockchainConfig {

    @Value("${blockchain.networkConfigPath}")
    private String networkConfigPath;
    
    @Value("${blockchain.walletPath}")
    private String walletPath;
    
    @Value("${blockchain.userName}")
    private String userName;
    
    @Value("${blockchain.channelName}")
    private String channelName;
    
    @Value("${blockchain.chaincodeName}")
    private String chaincodeName;

    @Bean
    public Gateway gateway() throws IOException {
        // Load a file system based wallet for managing identities
        Path walletDirectory = Paths.get(walletPath);
        Wallet wallet = Wallets.newFileSystemWallet(walletDirectory);

        // Path to network configuration file
        Path networkConfigFile = Paths.get(networkConfigPath);

        // Configure the gateway connection used to access the network
        Gateway.Builder builder = Gateway.createBuilder()
                .identity(wallet, userName)
                .networkConfig(networkConfigFile);

        return builder.connect();
    }

    @Bean
    public Network network(Gateway gateway) {
        // Get the network channel 
        return gateway.getNetwork(channelName);
    }

    @Bean
    public Contract userContract(Network network) {
        // Get the contract from the network
        return network.getContract(chaincodeName, "UserMgmt");
    }
} 