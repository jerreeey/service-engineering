package com.example.demo.service;

import com.example.demo.DTO.CarsDTO;
import com.example.demo.entity.Bookings;
import com.example.demo.entity.Cars;
import com.example.demo.repository.CarsRepository;
import com.example.demo.repository.UsersRepository;
import com.sun.xml.ws.api.message.Headers;
import com.sun.xml.ws.developer.WSBindingProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wsdl.ObjectFactory;
import wsdl.TestService;
import wsdl.UserCredentials;
import wsdl.WebService1;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.*;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.handler.Handler;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CarsService {

    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    @Autowired
    CarsRepository carsRepository;

    @Autowired
    UsersRepository usersRepository;

    @Transactional(readOnly = true)
    public List<CarsDTO> findCars(String pickupdate, String pickuphour, String returndate, String returnhour, String currency) throws ParseException {
        List<CarsDTO> carsDTOS = new ArrayList<>();
        List<Cars> cars = carsRepository.findAll();
        Date pickupDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(pickupdate + " " + pickuphour);
        Date returnDate = new SimpleDateFormat(DATE_TIME_FORMAT).parse(returndate + " " + returnhour);
        List<Cars> availableCars = new ArrayList<>();
        for (Cars car : cars) {
            Set<Bookings> bookings = car.getBookings();
            boolean carAvailable = bookings.stream().allMatch(b -> isNotInRange(b.getStartDate(), pickupDate, returnDate) && isNotInRange(b.getEndDate(), pickupDate, returnDate) || (b.getReturned()));
            if (carAvailable) {
                availableCars.add(car);
            }
        }
        availableCars.forEach(car -> carsDTOS.add(convertCarToCarDTO(car, currency)));
        return carsDTOS;
    }

    private boolean isNotInRange(Date date, Date startDate, Date endDate) {
        return date.before(startDate) || date.after(endDate);
    }

    private BigDecimal convertCurrency(BigDecimal amount, String from, String to) {

        wsdl.TestService service = new WebService1().getPort(TestService.class);
        wsdl.UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername("CarRental");
        userCredentials.setPassword("carRental123");

        WSBindingProvider bp = (WSBindingProvider) service;
        List<Handler> handlerChain = bp.getBinding().getHandlerChain();
        handlerChain.add(new SOAPHandler<SOAPMessageContext>() {
            @Override
            public Set<QName> getHeaders() {
                // Liste der Header-Namen, die dieser Handler bearbeitet
                return Collections.singleton(new QName("http://tempuri.org/", "UserCredentials"));
            }

            @Override
            public boolean handleMessage(SOAPMessageContext context) {
                Boolean outboundProperty = (Boolean)
                        context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
                if (outboundProperty.booleanValue()) {
                    try {
                        SOAPMessage soapMessage = context.getMessage();
                        SOAPHeader soapHeader = soapMessage.getSOAPHeader();
                        if (soapHeader == null) {
                            soapHeader = soapMessage.getSOAPPart().getEnvelope().addHeader();
                        }
                        QName userCredentialsQName = new QName("http://tempuri.org/", "UserCredentials");
                        SOAPHeaderElement userCredentialsHeader = soapHeader.addHeaderElement(userCredentialsQName);
                        SOAPElement username = userCredentialsHeader.addChildElement("username");
                        username.setTextContent(userCredentials.getUsername());
                        SOAPElement password = userCredentialsHeader.addChildElement("password");
                        password.setTextContent(userCredentials.getPassword());

                    } catch (SOAPException e) {
                        e.printStackTrace();
                    }
                }
                return true;
            }

            @Override
            public boolean handleFault(SOAPMessageContext context) {
                // Wenn ein Fehler auftritt, wird diese Methode aufgerufen
                return true;
            }

            @Override
            public void close(MessageContext context) {
                // Diese Methode wird aufgerufen, wenn der Handler geschlossen wird
            }
        });
        bp.getBinding().setHandlerChain(handlerChain);

        // HTTP Header hinzufügen
        bp.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, Boolean.TRUE);
        bp.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, "http://tempuri.org/ConvertCurrency");

        return service.convertCurrency(amount, from, to);
    }

    private CarsDTO convertCarToCarDTO(Cars car, String currency) {
        CarsDTO carsDTO = new CarsDTO();
        carsDTO.setCarId(car.getCarId());
        carsDTO.setMake(car.getMake());
        carsDTO.setModel(car.getModel());
        carsDTO.setYear(car.getYear());
        carsDTO.setCurrency(currency);
        carsDTO.setDailyRate(convertCurrency(BigDecimal.valueOf(car.getDailyRate()), car.getCurrency(), currency));
        return carsDTO;
    }

}
