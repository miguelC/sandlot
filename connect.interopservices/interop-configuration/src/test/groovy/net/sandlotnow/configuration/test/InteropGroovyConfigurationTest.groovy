//
// Generated from archetype; please customize.
//

package net.sandlotnow.configuration.test

import java.util.List;

import net.sandlotnow.interop.configuration.json.ConfigurationUtils;
import net.sandlotnow.interop.ihe.data.InteropIheDataService;
import net.sandlotnow.interop.ihe.model.EdgeSystem;
import net.sandlotnow.interop.ihe.model.EdgeTransaction;
import net.sandlotnow.interop.ihe.presentation.View;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import groovy.util.GroovyTestCase

/**
 * Tests for the {@link Example} class.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = [ "/context.xml" ])
class InteropGroovyConfigurationTest extends GroovyTestCase {
    
    Logger log = LoggerFactory.getLogger(InteropGroovyConfigurationTest.class);

//    @Autowired
//    private SpringConfigurationParser parser;

    @Autowired
    private InteropIheDataService iheDataService;
    
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    
    void testShow() {
        
    }
    
    @Test
    public void testJsonAllEdgeSystems(){

        List<EdgeSystem> edgesystems = iheDataService.listAllEdgeSystems()
        log.info("Edge systems : " + ConfigurationUtils.toJson(edgesystems, View.EdgeSystemSummary.class))
    }
    
    @Test
    public void testJsonAllEdgeSystemsEager(){

        List<EdgeSystem> edgesystems = iheDataService.listAllEdgeSystemsEager()
        edgesystems.each {
            it = ConfigurationUtils.cleanCircularRefs(it)
        }
        log.info("Edge systems EAGER: " + ConfigurationUtils.toJson(edgesystems, View.EdgeSystemComplete.class))
    }
}
