package org.apereo.cas.web.flow;

import org.apereo.cas.config.CasCoreMultifactorAuthenticationConfiguration;
import org.apereo.cas.web.flow.BaseWebflowConfigurerTests;
import org.apereo.cas.web.flow.config.CasMultifactorAuthenticationWebflowConfiguration;
import org.apereo.cas.web.flow.configurer.CompositeProviderSelectionMultifactorWebflowConfigurer;

import lombok.val;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.TransitionableState;

import static org.junit.jupiter.api.Assertions.*;

/**
 * This is {@link CompositeProviderSelectionMultifactorWebflowConfigurerTests}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@Import({
    ThymeleafAutoConfiguration.class,
    CasCoreMultifactorAuthenticationConfiguration.class,
    CasMultifactorAuthenticationWebflowConfiguration.class,
    BaseWebflowConfigurerTests.SharedTestConfiguration.class
})
public class CompositeProviderSelectionMultifactorWebflowConfigurerTests extends BaseWebflowConfigurerTests {
    @Test
    public void verifyOperation() {
        Assertions.assertFalse(casWebflowExecutionPlan.getWebflowConfigurers().isEmpty());
        val flow = (Flow) this.loginFlowDefinitionRegistry.getFlowDefinition(CasWebflowConfigurer.FLOW_ID_LOGIN);
        assertNotNull(flow);

        var state = (TransitionableState) flow.getState(CompositeProviderSelectionMultifactorWebflowConfigurer.MFA_COMPOSITE_EVENT_ID);
        assertNotNull(state);

        state = (TransitionableState) flow.getState(CasWebflowConstants.STATE_ID_REAL_SUBMIT);
        assertNotNull(state.getTransition(CompositeProviderSelectionMultifactorWebflowConfigurer.MFA_COMPOSITE_EVENT_ID));
    }
}
