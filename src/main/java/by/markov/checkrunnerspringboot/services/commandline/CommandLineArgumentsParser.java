package by.markov.checkrunnerspringboot.services.commandline;

import by.markov.checkrunnerspringboot.entities.ProductInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class CommandLineArgumentsParser {

    private final ProductInfo productInfo;


    public ProductInfo parseData(String[] args) {
        if (checkInputFormat(args)) {
            parseCommandLineArguments(args);
        }
        return productInfo;
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

    public void parseCommandLineArguments(String[] args) {
        for (String str : args) {
            String[] productAndAmount = str.split("-");
            if (productAndAmount[0].equalsIgnoreCase("card")) {
                productInfo.getDiscountCard().setNumber(Long.parseLong(productAndAmount[1]));
                break;
            } else {
                productInfo.getProductIds().add(Long.parseLong(productAndAmount[0]));
                productInfo.getProductAmount().add(Integer.parseInt(productAndAmount[1]));
            }
        }
    }
}