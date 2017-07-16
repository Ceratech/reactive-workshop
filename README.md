## Nodig

Intellij met Scala plugin

## Openen project

Importeer project in Intellij: `File -> New -> Project from existing sources...`. Kies bij import optie `Import from external model` en kies `SBT`. Zorg dat er op het volgende scherm een JDK 1.8 geselecteerd staat en kies `Finnish`.

## Runnen project

Open het `Configurations` menu in Intellij; voeg hier een nieuwe configuratie aan toe op basis van een `Play 2 app`. Run deze configuratie; de browser zal vanzelf openen als de app opgestart is.

## Beschikbare calls

De API ondersteunt de volgende calls:

* `GET /pizzas/namen`, alle pizza namen ophalen
* `GET /pizzas`, alle pizzas met hun details ophalen
* `GET /pizzas/:pizzaId`, een pizza met details ophalen voor het gegeven id
* `POST /pizzas`, een nieuwe pizza toevoegen of een bestaande updaten

De `POST` body voor de update/toevoegen call is als volgt:
```
{
    "naam": "Margharita",
    "toppings": [
        {
            "ingredient": "Mozzarella",
            "aantal": 3
        }
    ]
}
```

## Unit test

Er is een (vrij kale) unit-test voor de `PizzaService`, deze is te vinden in de `PizzaServiceSpec` file. De test kan je draaien door rechtermuisknop op de file en kies voor `Run`. 