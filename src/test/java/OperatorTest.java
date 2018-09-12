import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

public class OperatorTest {
    @Test
    void map() { // Transforma um objeto em outro
        Flux.range(1,5)
                .map(i -> i * 10)
                .subscribe(System.out::println);
    }

    @Test
    void flatMap() { // Necessário retornar um Flux ou um Mono
        Flux.range(1,5)
                .flatMap(i -> Flux.range(i * 10, 2))
                .subscribe(System.out::println);
    }

    @Test
    void flatMapMany() { // Converte um Mono em um Flux
        Mono.just(3)
                .flatMapMany(i -> Flux.range(1, i))
                .subscribe(System.out::println);
    }

    @Test
    void concat() throws InterruptedException { // Junta os publishers de maneira sequencial
        Flux<Integer> oneToFive = Flux.range(1,5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5)
                .delayElements(Duration.ofMillis(400));

        Flux.concat(oneToFive, sixToTen)
                .subscribe(System.out::println);

//        oneToFive.concatWith(sixToTen) // concatWith retorna um Flux mesmo se usado com um Mono
//                .subscribe(System.out::println);

        Thread.sleep(4000);
    }

    @Test
    void merge() throws InterruptedException { // Junta os publishers de maneira não sequencial, entrelaça os valores
        Flux<Integer> oneToFive = Flux.range(1,5)
                .delayElements(Duration.ofMillis(200));
        Flux<Integer> sixToTen = Flux.range(6,5)
                .delayElements(Duration.ofMillis(400));

        Flux.merge(oneToFive, sixToTen)
                .subscribe(System.out::println);

//        oneToFive.mergeWith(sixToTen) // mergeWith retorna um Flux mesmo se usado com um Mono
//                .subscribe(System.out::println);

        Thread.sleep(4000);
    }

    @Test
    void zip() { // Combina dois ou mais publishers de acordo com a função definida no lambda
        Flux<Integer> oneToFive = Flux.range(1,5);
        Flux<Integer> sixToTen = Flux.range(6,5);

        Flux.zip(oneToFive, sixToTen, // Método estático
                (item1, item2) -> item1 + ", " + item2)
                .subscribe(System.out::println);

//        oneToFive.zipWith(sixToTen)
//                .subscribe(System.out::println);
    }
}
