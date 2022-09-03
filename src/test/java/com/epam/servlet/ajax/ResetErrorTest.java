package com.epam.servlet.ajax;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ResetErrorTest {

    @Test
    public void resetErrorTest() throws ServletException, IOException {
        ResetError resetError = new ResetError();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("showtimeError")).thenReturn(null);

        resetError.doGet(request,response);

        Assertions.assertNull(session.getAttribute("showtimeError"));




    }
}
