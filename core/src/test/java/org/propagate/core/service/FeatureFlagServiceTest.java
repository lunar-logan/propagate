package org.propagate.core.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.propagate.common.dao.FeatureFlagDao;
import org.propagate.common.domain.FeatureFlag;
import org.propagate.common.domain.FeatureFlagType;
import org.propagate.common.domain.RolloutRules;
import org.propagate.common.domain.rollout.RolloutRule;
import org.propagate.core.service.impl.FeatureFlagServiceImpl;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

class FeatureFlagServiceTest extends AbstractFeatureFlagTest {
    private FeatureFlagService featureFlagService;
    private final List<RolloutRule> rules = List.of(percentRule(List.of(percent(10, "true"), percent(90, "false"))));
    private final RolloutRules prod = rollout("prod", variation("false"), variation("false"), rules);
    private final RolloutRules staging = rollout("stg", variation("false"), variation("false"), rules);
    private final FeatureFlag ff = createFeatureFlag(List.of(prod, staging));


    @BeforeEach
    public void setup() {
        final FeatureFlagDao mockedDao = Mockito.mock(FeatureFlagDao.class);
        when(mockedDao.save(any(FeatureFlag.class))).thenReturn(ff);
        featureFlagService = new FeatureFlagServiceImpl(mockedDao);
    }

    @Test
    public void createFeatureFlag__correctly_creates_feature_flag() {
        FeatureFlag actualFF = featureFlagService.createFeatureFlag(ff);
        assertEquals("1", actualFF.getId());
        assertEquals("partial-delivery-rollout", actualFF.getKey());
        assertEquals(FeatureFlagType.BOOLEAN, actualFF.getType());
        assertFalse(actualFF.isTargeting());
        assertEquals(List.of(variation("true"), variation("false")), actualFF.getVariations());
        assertEquals(2, actualFF.getRolloutRules().size());
        assertEquals("prod", actualFF.getRolloutRules().get(0).getNamespace());
        assertEquals(rules, actualFF.getRolloutRules().get(0).getRolloutRules());
        assertEquals("stg", actualFF.getRolloutRules().get(1).getNamespace());
        assertEquals(rules, actualFF.getRolloutRules().get(1).getRolloutRules());
    }

    @Test
    public void createFeatureFlag__fails_when_variation_mismatches_between_declared_and_rollout_rules() {
        final List<RolloutRule> rules = List.of(percentRule(List.of(percent(10, "true"), percent(90, "false"))));
        final RolloutRules prod = rollout("prod", variation("false"), variation("some variation"), rules);
        final FeatureFlag ff = createFeatureFlag(List.of(prod));
        assertThrows(IllegalArgumentException.class, () -> featureFlagService.createFeatureFlag(ff));
    }

    @Test
    public void createFeatureFlag__fails_when_variation_mismatches_between_declared_and_rollout_rule() {
        final List<RolloutRule> rules = List.of(percentRule(List.of(percent(10, "some other value"), percent(90, "false"))));
        final RolloutRules prod = rollout("prod", variation("false"), variation("false"), rules);
        final FeatureFlag ff = createFeatureFlag(List.of(prod));
        assertThrows(IllegalArgumentException.class, () -> featureFlagService.createFeatureFlag(ff));
    }

    @Test
    public void createFeatureFlag__fails_when_type_boolean_and_variation_not_equal_to_true_or_false() {
        final List<RolloutRule> rules = List.of(percentRule(List.of(percent(10, "true"), percent(90, "false"))));
        final RolloutRules prod = rollout("prod", variation("false"), variation("false"), rules);
        final FeatureFlag ff = createFeatureFlag(List.of(prod));
        ff.setVariations(List.of(variation("help"), variation("me")));
        assertThrows(IllegalArgumentException.class, () -> featureFlagService.createFeatureFlag(ff));
    }

    @Test
    public void createFeatureFlag__fails_for_percent_distribution_with_sum_not_equal_to_100() {
        final List<RolloutRule> rules = List.of(percentRule(List.of(percent(10, "true"), percent(20, "false"))));
        final RolloutRules prod = rollout("prod", variation("false"), variation("false"), rules);
        final FeatureFlag ff = createFeatureFlag(List.of(prod));
        assertThrows(IllegalArgumentException.class, () -> featureFlagService.createFeatureFlag(ff));
    }

    private FeatureFlag createFeatureFlag(List<RolloutRules> rules) {
        return FeatureFlag.builder()
                .id("1")
                .name("Partial Delivery Rollout")
                .key("partial-delivery-rollout")
                .type(FeatureFlagType.BOOLEAN)
                .variations(List.of(variation("true"), variation("false")))
                .rolloutRules(rules)
                .targeting(false)
                .archived(false)
                .created(new Date())
                .build();
    }
}