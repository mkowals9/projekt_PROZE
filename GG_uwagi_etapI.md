Szanowni Studenci,

- funkcjonalność sieciowa - niezależnie od realizacji "serwera konfiguracyjnego" gra powinna dysponować opcji gry lokalnej z wykorzystaniem plików konfiguracyjnych;

- "(...)niezbędne pliki konfiguracyjne umieszczone będą na serwerze oraz pobierane za pomocą odpowiednich zapytań GET(...)": 
po pierwsze, to musi być własny serwer (samodzielnie napisana aplikacja); 
po drugie, pobierane mają być parametry konfiguracyjne (pojedyńczo bądź w logicznie powiązanych grupach), a nie pliki konfiguracyjne 
(proszę sobie wyobrazić, tak dla przykładu, że serwer przechowuje parametry konfiguracyjne w bazie danych, a nie w plikach).

- punktacja: wszelkie konkretne wartości, takie jak stała za poziom czy mnożnik za poziom trudności, itd., 
a przy okazji także początkowa liczba żyć itp., powinny wynikać z plików konfiguracyjnych 
(serwera konfiguracyjnego w wersji sieciowej) - co Państwo planują, ale tak tylko zwracam uwagę, że dotyczy to WSYSTKICH parametrów rozgrywki :-).


Etap zaliczony.

Zwracam uwagę na (komentarz ogólny, do wszystkich):

1. wszelkie "PARAMETRY" gry, takie jak: liczba punktów za określone elementy (wroga, poziom, czas, itd.), początkowa liczba żyć... 
oraz wszystkie inne tego typu parametry związane z rozgrywką (nie GUI!) powinny być odczytywane z konfiguracji a NIE zapisane na stałe w kodzie;<br/>
  wygląd plansz (w tym m.in. ich wielkość - liczba wierszy/kolumn, ale także liczba i początkowe rozmieszczenie wrogów) oczywiście też z konfiguracji - jeśli 
różne poziomy trudności: to różne opisy? albo chociaż 'modyfikatory' zapisane w konfiguracji (jak np. współczynniki zdobytych punktów za poziomy trudności); <br/>
  w szczególności liczba plansz składających się na grę (-> scenariusz gry) MUSI wynikać z konfiguracji (np. jawny parametr; 
albo określona konwencja nazywania plików z definicją planszy - brak kolejnego pliku oznacza zakończenie gry; 
albo taki zapis plansz, z którego wynika ich liczba; albo...);

2. dokumentację należy tworzyć na bieżąco;

3. zapoznać się z wszystkimi wymaganiami (uwagi ogólne - 11 punktów; co wymagane na zaliczeniu projektu; co na kolejne etapy...) - nie wszystkie wymagania "szczegółowe" mają swoje bezpośrednie odzwierciedlenie w etapach, są sprawdzane dopiero przy zaliczeniu projektu, a ich brak skutkuje obniżeniem oceny;

4. na kolejny etap przygotować "moduł odczytu i parsowania plików konfiguracyjnych": MODUŁ, czyli jakąś odrębną klasę (pakiet... interfejs... - myśleć od razu przyszłościowo o funkcjonalności sieciowej!) odczytującą konfigurację i udostępniającą wartości parametrów poprzez wygodny interfejs, a nie jakąś tam "metodę pomocniczą" działającą "tu i teraz";
  a także wszystkie inne elementy zgodnie z opisem na stronie projektu


Z poważaniem,<br/>
Grzegorz Galiński
