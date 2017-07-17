## Nodig

Intellij met Scala plugin; via plugins en zoeken op `Scala` (http://www.jetbrains.net/confluence/display/SCA/Scala+Plugin+for+IntelliJ+IDEA).

## Openen project

Als je de Scala plugin geinstalleerd hebt kun je het project openen via `File -> open`.

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

### Postman

In het project zit een `Pizza API.postman_collection.json` welke alle requests bevat in Postman formaat, deze kan je importeren in Postman om makkelijk de requests uit te voeren. Via Postman is het de grote `Import` knop bovenin.

## Unit test

Er is een (vrij kale) unit-test voor de `PizzaService`, deze is te vinden in de `PizzaServiceSpec` file. De test kan je draaien door rechtermuisknop op de file en kies voor `Run`. 