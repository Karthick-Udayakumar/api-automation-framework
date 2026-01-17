package com.restfulbooker.tests;

import com.framework.constants.StatusCodes;
import com.framework.utils.AssertionUtils;
import com.framework.utils.JsonUtils;
import com.framework.utils.TestDataGenerator;
import models.Booking;
import models.BookingDates;
import models.BookingResponse;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("Restful Booker API")
@Feature("Booking Management")
@Owner("QA Team")
public class BookingTests extends BaseTest {

    private int createdBookingId;

    @Severity(SeverityLevel.CRITICAL)
    @Story("Create Booking")
    @Test(priority = 1, description = "Create a new booking successfully")
    public void testCreateBooking() {
        BookingDates dates = new BookingDates(
                TestDataGenerator.generateFutureDate(5),
                TestDataGenerator.generateFutureDate(10)
        );

        Booking booking = new Booking(
                TestDataGenerator.generateFirstName(),
                TestDataGenerator.generateLastName(),
                TestDataGenerator.generatePrice(100, 1000),
                TestDataGenerator.generateBoolean(),
                dates,
                "Breakfast"
        );

        Response response = bookingService.createBooking(booking);
        AssertionUtils.verifyStatusCode(response, StatusCodes.OK);

        BookingResponse bookingResponse = JsonUtils.deserialize(response, BookingResponse.class);
        createdBookingId = bookingResponse.getBookingid();

        Assert.assertTrue(createdBookingId > 0, "Booking ID should be positive");
        System.out.println("✓ Created Booking ID: " + createdBookingId);
    }

    @Severity(SeverityLevel.NORMAL)
    @Story("Get Booking")
    @Test(priority = 2, dependsOnMethods = "testCreateBooking",
            description = "Retrieve booking by ID")
    public void testGetBookingById() {
        Response response = bookingService.getBookingById(createdBookingId);
        AssertionUtils.verifyStatusCode(response, StatusCodes.OK);

        Booking booking = JsonUtils.deserialize(response, Booking.class);
        AssertionUtils.verifyNotNull(booking.getFirstname(), "Firstname");
        System.out.println("✓ Retrieved Booking: " + booking.getFirstname() + " " + booking.getLastname());
    }

    @Severity(SeverityLevel.CRITICAL)
    @Story("Update Booking")
    @Test(priority = 3, dependsOnMethods = "testCreateBooking",
            description = "Update existing booking")
    public void testUpdateBooking() {
        BookingDates dates = new BookingDates(
                TestDataGenerator.generateFutureDate(15),
                TestDataGenerator.generateFutureDate(20)
        );

        Booking updatedBooking = new Booking(
                "Jane",
                "Smith",
                750,
                true,
                dates,
                "Lunch"
        );

        Response response = bookingService.updateBooking(createdBookingId, updatedBooking);
        AssertionUtils.verifyStatusCode(response, StatusCodes.OK);

        Booking returned = JsonUtils.deserialize(response, Booking.class);
        Assert.assertEquals(returned.getFirstname(), "Jane");
        System.out.println("✓ Updated Booking ID: " + returned.getFirstname());
    }

    @Severity(SeverityLevel.BLOCKER)
    @Story("Delete Booking")
    @Test(priority = 4, dependsOnMethods = "testCreateBooking",
            description = "Delete booking by ID")
    public void testDeleteBooking() {
        Response deleteResp = bookingService.deleteBooking(createdBookingId);
        AssertionUtils.verifyStatusCode(deleteResp, StatusCodes.CREATED);

        Response getResp = bookingService.getBookingById(createdBookingId);
        AssertionUtils.verifyStatusCode(getResp, StatusCodes.NOT_FOUND);

        System.out.println("✓ Booking deleted successfully!");
    }
}
