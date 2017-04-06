/*
 * Copyright 2000-2017 Vaadin Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.vaadin.hummingbird.uitest.ui.template;

import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.vaadin.hummingbird.testutil.ChromeBrowserTest;

public class SubPropertyModelIT extends ChromeBrowserTest {

    @Test
    public void subproperties() {
        open();

        WebElement template = findElement(By.id("template"));
        Assert.assertEquals("message",
                getInShadowRoot(template, By.id("msg")).get().getText());

        getInShadowRoot(template, By.id("button")).get().click();

        Assert.assertEquals("Updated",
                getInShadowRoot(template, By.id("msg")).get().getText());

        getInShadowRoot(template, By.id("sync")).get().click();

        WebElement syncedReport = findElement(By.id("synced-msg"));
        Assert.assertEquals("Set from the client", syncedReport.getText());

        WebElement input = getInShadowRoot(template, By.id("input")).get();
        input.clear();
        input.sendKeys("foo");

        List<WebElement> valueUpdate = findElements(By.id("value-update"));
        Optional<WebElement> result = valueUpdate.stream()
                .filter(element -> element.getText().equals("foo")).findAny();
        Assert.assertTrue(
                "Unable to find updated input value element. "
                        + "Looks like input hasn't sent an event for subproperty",
                result.isPresent());

        // click message
        getInShadowRoot(template, By.id("msg")).get().click();

        Assert.assertEquals(findElement(By.id("statusClick")).getText(),
                getInShadowRoot(template, By.id("msg")).get().getText());
    }
}
