package by.markov.checkrunnerspringboot.services.commandline;

import by.markov.checkrunnerspringboot.entities.DiscountCard;
import by.markov.checkrunnerspringboot.services.orders.OrderManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommandLineArgumentsParser {

    private final OrderManager orderManager;
    private final List<Long> productIds;
    private final List<Integer> productAmount;


    public void start(String[] args) {
        if (checkInputFormat(args)) {
            DiscountCard discountCard = parseCommandLineArgumentsAndDelegateCreateOrder(args);
            orderManager.createOrderAndDelegateCreateCheck(productIds, productAmount, discountCard);
        }
    }

    public boolean checkInputFormat(String[] args) {
        Pattern pattern = Pattern.compile("(\\d*-\\d*)|(card-\\d{4})");
        Matcher result;
        for (String str : args) {
            result = pattern.matcher(str);
            if (!result.matches()) {
                System.out.println("Input Data Format Is Not Correct.");
                return false;
            }
        }
        return true;
    }

    public DiscountCard parseCommandLineArgumentsAndDelegateCreateOrder(String[] args) {
        long cardNumber = 0;
        for (String str : args) {
            String[] productAndAmount = str.split("-");
            if (productAndAmount[0].equalsIgnoreCase("card")) {
                cardNumber = Long.parseLong(productAndAmount[1]);
            } else {
                productIds.add(Long.parseLong(productAndAmount[0]));
                productAmount.add(Integer.parseInt(productAndAmount[1]));
            }
        }
        return DiscountCard.builder()
                .number(cardNumber)
                .build();
    }
}