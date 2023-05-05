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

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
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

        //mypackage.WebService1Soap service = new WebService1().getPort(WebService1Soap.class);

        wsdl.TestService service = new WebService1().getPort(TestService.class);
        wsdl.UserCredentials userCredentials = new UserCredentials();
        userCredentials.setUsername("CarRental");
        userCredentials.setPassword("carRental123");
        JAXBElement<UserCredentials> usercredentials = new ObjectFactory().createUserCredentials(userCredentials);
        Map requestCtx = ((BindingProvider) service).getRequestContext();
        requestCtx.put(UserCredentials.class.getName(), userCredentials);
        requestCtx.put(BindingProvider.USERNAME_PROPERTY, "CarRental");
        requestCtx.put(BindingProvider.PASSWORD_PROPERTY, "carRental123");
        String productionUrl = "https://currencyconvertercarrental.azurewebsites.net/CurrencyConverterWS.asmx?wsdl";
        requestCtx.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, productionUrl);
        return service.convertCurrency(amount, from, to);
        //return BigDecimal.valueOf(50);
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
