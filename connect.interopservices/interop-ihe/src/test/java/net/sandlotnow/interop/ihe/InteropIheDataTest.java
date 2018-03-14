package net.sandlotnow.interop.ihe;


import static org.junit.Assert.*;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import net.sandlotnow.interop.ihe.data.InteropIheDataService;
import net.sandlotnow.interop.ihe.model.EdgeEndpoint;
import net.sandlotnow.interop.ihe.model.EdgeOrganization;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners({DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = { "/context.xml" })
public class InteropIheDataTest {

    private static final Logger log = LoggerFactory.getLogger(InteropIheDataTest.class);
    
    @Autowired
    private InteropIheDataService iheDataService;
    
    @Autowired
    private TestDataIhe testData;
    
    @Before
    public void setUp() throws Exception {
        for(EdgeTransaction tx : testData.getTransactions()){
            EdgeTransaction dbTx = iheDataService.findEdgeTransactionByName(tx.getName());
            if(dbTx == null){
                tx = iheDataService.saveEdgeTransaction(tx);
                
            }
            
        }
    }

    @After
    public void tearDown() throws Exception {
    }

    //@Test
    public void testAddEdgeSystem() throws Exception {
        for(EdgeSystem edge : testData.getEdgeSystems()){
            EdgeSystem dbEdge = iheDataService.findEdgeSystemByAssigningAuthorityOID(edge.getAssigningAuthorityOID());
            if(dbEdge == null){
                log.info("Edge System not found already in database... storing .. " + edge.toString());
                edge = iheDataService.saveEdgeSystem(edge);
            }          
            
        }
        
        
    }

    //@Test
    public void testAddEdgeSystemJson() throws Exception {
        
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        EdgeOrganization org = mapper.readValue(
                new File(getClass().getClassLoader().getResource("org.json").getFile()), 
                EdgeOrganization.class);
        
        org = iheDataService.saveEdgeOrganization(org);        
        
    }
    
    @Test
    public void testEdgeSystemQuery(){

        EdgeSystem dbEdge = iheDataService.findEdgeSystemById(2);
        log.info("Edge system by ID : " + (dbEdge == null ? "NOT FOUND" : dbEdge.toString()));
    }

    
    @Test
    public void testEdgeSystemByDevice(){

        EdgeSystem dbEdge = iheDataService.findEdgeSystemByDeviceOrAssigningAuthorityOID("1.2.840.114350.1.13.60.2.7.2.688879");
        log.info("Edge system by DEVICE : " + (dbEdge == null ? "NOT FOUND" : dbEdge.toString()));
    }
    
    @Test
    public void testAllEdgeSystemsLazy(){

        List<EdgeSystem> edgesystems = iheDataService.listAllEdgeSystems();
        log.info("Edge systems LAZY found " + (edgesystems == null ? "0" : edgesystems.size()) + " edge systems.");
        if(edgesystems != null){
            for(EdgeSystem edge : edgesystems) {
                log.info("Edge system LAZY : " + edge.getName());
            }
        }
    }
    
    @Test
    public void testAllEdgeSystemsEager(){

        List<EdgeSystem> edgesystems = iheDataService.listAllEdgeSystemsEager();
        log.info("Edge systems EAGER found " + (edgesystems == null ? "0" : edgesystems.size()) + " edge systems.");
        if(edgesystems != null){
            for(EdgeSystem edge : edgesystems) {
                log.info("Edge system EAGER : " + edge.toString());
            }
        }
    }
}
