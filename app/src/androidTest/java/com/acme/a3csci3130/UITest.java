package com.acme.a3csci3130;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static org.hamcrest.CoreMatchers.anything;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.onData;

import static android.support.test.espresso.action.ViewActions.*;
import static android.support.test.espresso.matcher.ViewMatchers.*;


import android.support.test.rule.ActivityTestRule;
import android.widget.ListView;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Espresso test
 *
 * @error may take time to read database
 *      solution: wait for a while or close the emulator and re-enter
 *
 * @author OwenZhang
 */
@RunWith(AndroidJUnit4.class)
public class UITest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.acme.a3csci3130", appContext.getPackageName());
    }

    @Rule
    public final ActivityTestRule<MainActivity> CRUDTest = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void tog (){

        createContactTestSuccess();
        readTest();
        updateTestSuccess();
        eraseTest();
    }

    /**
     * This test will fail to create a contact since the primary business is not valid
     *
     */
    @Test
    public void createContactTestFail() {
        onView(withId(R.id.submitButtonCreate)).perform(click());
        onView(withId(R.id.name4)).perform(typeText("Ozan"));

        onView(withId(R.id.business_number_input4)).perform(typeText("" + ((int) (Math.random() * 899999999) + 100000000)));
        onView(withId(R.id.primary_business_input4)).perform(typeText("Fish"));
        onView(withId(R.id.submitButtonCreate)).perform(click());
        onView(withId(R.id.testView)).check(matches(withText("Primary Business information is invalid\n")));


    }

    /**
     * This test will create a new contact
     */
    @Test
    public void createContactTestSuccess() {

        onView(withId(R.id.submitButtonCreate)).perform(click());

        int init = ((ListView) CRUDTest.getActivity().findViewById(R.id.listView)).getAdapter().getCount();

        onView(withId(R.id.name4)).perform(typeText("" + (char) ((Math.random() * 26) + 'a') + (char) ((Math.random() * 26) + 'a')));

        onView(withId(R.id.business_number_input4)).perform(typeText("" + ((int) (Math.random() * 899999999) + 100000000)));
        onView(withId(R.id.primary_business_input4)).perform(typeText("Fisher"));
        onView(withId(R.id.submitButtonCreate)).perform(click());

        int fin = ((ListView) CRUDTest.getActivity().findViewById(R.id.listView)).getAdapter().getCount();

        assertEquals(fin - init, 1);
    }

    /**
     * This test will check is the second data matches a certain people
     */
    @Test
    public void readTest() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(1).perform(click());
        onView(withId(R.id.name)).check(matches(withText("Mtt")));
        onView(withId(R.id.primary_business_input)).check(matches(withText("Fisher")));
        onView(withId(R.id.address_input)).check(matches(withText("1634 Noeth Str.")));
        onView(withId(R.id.province_territory_input)).check(matches(withText("NS")));
        onView(withId(R.id.business_number_input)).check(matches(withText("678678678")));
        onView(withId(R.id.updateButton)).perform(click());
    }

    /**
     * This is to update a contact and check is the changed data same as the one desired
     */
    @Test
    public void updateTestSuccess() {
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
        }
        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(2).perform(click());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        onView(withId(R.id.province_territory_input)).perform(clearText());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        onView(withId(R.id.province_territory_input)).perform(typeText("AB"));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        onView(withId(R.id.address_input)).perform(clearText());
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        onView(withId(R.id.address_input)).perform(typeText("Apt 2, Noeth Street"));
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
        }
        onView(withId(R.id.updateButton)).perform(click());

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(2).perform(click());
        onView(withId(R.id.name)).check(matches(withText("hp")));
        onView(withId(R.id.primary_business_input)).check(matches(withText("Fisher")));
        onView(withId(R.id.address_input)).check(matches(withText("Apt 2, Noeth Street")));
        onView(withId(R.id.province_territory_input)).check(matches(withText("AB")));
        onView(withId(R.id.business_number_input)).check(matches(withText("448201101")));
        onView(withId(R.id.updateButton)).perform(click());

    }

    /**
     * erase a contact
     */
    @Test
    public void eraseTest() {
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }

        int init = ((ListView) CRUDTest.getActivity().findViewById(R.id.listView)).getAdapter().getCount();
        //Espresso.onData(anything()).inAdapterView(withId(R.id.listView));

        onData(anything()).inAdapterView(withId(R.id.listView)).atPosition(3).perform(click());

        onView(withId(R.id.deleteButton)).perform(click());

        int fin = ((ListView) CRUDTest.getActivity().findViewById(R.id.listView)).getAdapter().getCount();

        assertEquals(init - fin, 1);

    }
}
