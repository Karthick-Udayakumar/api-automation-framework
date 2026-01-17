package services;

import com.framework.base.BaseAPI;
import com.framework.base.SpecBuilder;
import com.framework.utils.JsonUtils;
import constants.Endpoints;
import models.Booking;
import models.BookingResponse;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class BookingService extends BaseAPI {

    @Step("Get all booking IDs")
    public Response getAllBookingIds() {
        return get(SpecBuilder.getBaseSpec(), Endpoints.BOOKING);
    }

    @Step("Get booking by ID: {bookingId}")
    public Response getBookingById(int bookingId) {
        return get(SpecBuilder.getBaseSpec(), Endpoints.BOOKING + "/" + bookingId);
    }

    @Step("Create new booking")
    public Response createBooking(Booking booking) {
        return post(SpecBuilder.getBaseSpec(), Endpoints.BOOKING, booking);
    }

    public BookingResponse createBookingAndGetResponse(Booking booking) {
        Response response = createBooking(booking);
        return JsonUtils.deserialize(response, BookingResponse.class);
    }

    @Step("Update booking: {bookingId}")
    public Response updateBooking(int bookingId, Booking booking) {
        return put(SpecBuilder.getSpecWithCookieAuth(), Endpoints.BOOKING + "/" + bookingId, booking);
    }

    @Step("Partial update booking: {bookingId}")
    public Response partialUpdateBooking(int bookingId, Object partial) {
        return patch(SpecBuilder.getSpecWithCookieAuth(), Endpoints.BOOKING + "/" + bookingId, partial);
    }

    @Step("Delete booking: {bookingId}")
    public Response deleteBooking(int bookingId) {
        return delete(SpecBuilder.getSpecWithCookieAuth(), Endpoints.BOOKING + "/" + bookingId);
    }
}

