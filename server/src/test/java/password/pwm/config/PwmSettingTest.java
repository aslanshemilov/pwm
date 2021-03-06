/*
 * Password Management Servlets (PWM)
 * http://www.pwm-project.org
 *
 * Copyright (c) 2006-2009 Novell, Inc.
 * Copyright (c) 2009-2018 The PWM Project
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package password.pwm.config;

import org.junit.Assert;
import org.junit.Test;
import password.pwm.PwmConstants;
import password.pwm.error.PwmOperationalException;
import password.pwm.error.PwmUnrecoverableException;
import password.pwm.util.secure.PwmSecurityKey;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class PwmSettingTest
{

    @Test
    public void testDefaultValues() throws PwmUnrecoverableException, PwmOperationalException
    {
        final PwmSecurityKey pwmSecurityKey = new PwmSecurityKey( "abcdefghijklmnopqrstuvwxyz" );
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            for ( final PwmSettingTemplate template : PwmSettingTemplate.values() )
            {
                final PwmSettingTemplateSet templateSet = new PwmSettingTemplateSet( Collections.singleton( template ) );
                final StoredValue storedValue = pwmSetting.getDefaultValue( templateSet );
                storedValue.toNativeObject();
                storedValue.toDebugString( PwmConstants.DEFAULT_LOCALE );
                storedValue.toDebugJsonObject( PwmConstants.DEFAULT_LOCALE );
                storedValue.toXmlValues( "value", pwmSecurityKey );
                storedValue.validateValue( pwmSetting );
                storedValue.requiresStoredUpdate();
                Assert.assertNotNull( storedValue.valueHash() );
            }
        }
    }

    @Test
    public void testDescriptions() throws PwmUnrecoverableException, PwmOperationalException
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            try
            {
                pwmSetting.getDescription( PwmConstants.DEFAULT_LOCALE );
            }
            catch ( final Throwable t )
            {
                throw new IllegalStateException( "unable to read description for setting '" + pwmSetting.toString() + "', error: " + t.getMessage(), t );
            }
        }
    }

    @Test
    public void testLabels() throws PwmUnrecoverableException, PwmOperationalException
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            try
            {
                pwmSetting.getLabel( PwmConstants.DEFAULT_LOCALE );
            }
            catch ( final Throwable t )
            {
                throw new IllegalStateException( "unable to read label for setting '" + pwmSetting.toString() + "', error: " + t.getMessage(), t );
            }
        }
    }

    @Test
    public void testFlags() throws PwmUnrecoverableException, PwmOperationalException
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            pwmSetting.getFlags();
        }
    }

    @Test
    public void testProperties() throws PwmUnrecoverableException, PwmOperationalException
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            try
            {
                pwmSetting.getProperties();
            }
            catch ( final Throwable t )
            {
                throw new IllegalStateException( "unable to read properties for setting '" + pwmSetting.toString() + "', error: " + t.getMessage(), t );
            }
        }
    }

    @Test
    public void testOptions() throws PwmUnrecoverableException, PwmOperationalException
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            try
            {
                pwmSetting.getOptions();
            }
            catch ( final Throwable t )
            {
                throw new IllegalStateException( "unable to read options for setting '" + pwmSetting.toString() + "', error: " + t.getMessage(), t );
            }

        }
    }

    @Test
    public void testRegExPatterns() throws PwmUnrecoverableException, PwmOperationalException
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            pwmSetting.getRegExPattern();
        }
    }

    @Test
    public void testKeyUniqueness()
    {
        final Set<String> seenKeys = new HashSet<>();
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            // duplicate key foud
            Assert.assertTrue( !seenKeys.contains( pwmSetting.getKey() ) );
            seenKeys.add( pwmSetting.getKey() );
        }
    }

    @Test
    public void testMinMaxValueRanges()
    {
        for ( final PwmSetting pwmSetting : PwmSetting.values() )
        {
            final long minValue = Long.parseLong( pwmSetting.getProperties().getOrDefault( PwmSettingProperty.Minimum, "0" ) );
            final long maxValue = Long.parseLong( pwmSetting.getProperties().getOrDefault( PwmSettingProperty.Maximum, "0" ) );
            if ( maxValue != 0 )
            {
                Assert.assertTrue( maxValue > minValue );
            }
        }

    }
}
