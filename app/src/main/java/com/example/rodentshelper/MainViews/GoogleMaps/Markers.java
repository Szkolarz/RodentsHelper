package com.example.rodentshelper.MainViews.GoogleMaps;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.rodentshelper.R;
import com.example.rodentshelper.ROOM.Rodent.ViewRodents;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.List;

public class Markers implements GoogleMap.OnMarkerClickListener {


    public void createMarkers(GoogleMap map) {

        recommendedMarkers(map);
        recommendedByUsers(map);
        addedByAuthor(map);

    }

    private void addBlueMarker(GoogleMap map, String title, double latitude1, double latitude2,
                              String address, String phoneNumber) {
        map.addMarker(new MarkerOptions()
                .title(title)
                .position(new LatLng (latitude1, latitude2))
                .snippet(address + "\n" + phoneNumber)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    }

    private void addVioletMarker(GoogleMap map, String title, double latitude1, double latitude2,
        String address, String phoneNumber) {

        map.addMarker(new MarkerOptions()
                .title(title)
                .position(new LatLng (latitude1, latitude2))
                .snippet(address + "\n" + phoneNumber)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
    }



    private void addedByAuthor (GoogleMap map) {

        addVioletMarker(map, "Dudkowiak S., lek. wet. Lecznica dla zwierząt", 50.598249864718504, 17.03987761354501,
                "Przemysłowa 5, 57-220 Ziębice", "748191266");

        addVioletMarker(map, "Przychodnia Weterynaryjna ProVetica", 50.46965478801537, 17.33820249372764,
                "Mariacka 7/1, 48-304 Nysa", "666698839");

        addVioletMarker(map, "Gabinet Weterynaryjny Kameleon", 52.17748629878843, 22.303823502890594,
                "Kazimierzowska 33, 08-110 Siedlce", "693100035");

        addVioletMarker(map, "KamVet Przychodnia Weterynaryjna", 54.097156772991006, 22.9309307302848,
                "Wesoła 16, 16-400 Suwałki", "601516104");

        addVioletMarker(map, "Przychodnia weterynaryjna VetMedica", 53.69691311764739, 17.543696597453856,
                "Ildefonsa Gałczyńskiego 29, 89-600 Chojnice", "731731707");

        addVioletMarker(map, "Przychodnia weterynaryjna \"PRO ANIMAL\"", 53.767542631261136, 16.090364770917905,
                "Mickiewicza 4, 78-320 Połczyn-Zdrój", "502667450");

        addVioletMarker(map, "Przychodnia dla zwierząt - Marek Leończak\n", 53.162441270247086, 22.091491219490372,
                "Hipokratesa 40, 18-400 Łomża", "862182053");

        addVioletMarker(map, "Centrum Zdrowia Małych Zwierząt \"Vet-KoR\" Rafał Kopyto", 50.84737144665186, 19.13071416519294,
                "Generała Stefana Roweckiego \"Grota\" 12/45, 42-224 Częstochowa", "512502100");

        addVioletMarker(map, "Banach Zbigniew, lek. wet. Usługi Weterynaryjne", 51.57710228505702, 18.711979029471806,
                "Jana Pawła II 101, 98-200 Sieradz", "669784949");

        addVioletMarker(map, "\uD83C\uDFAF Gallus Przychodnia weterynaryjna. Weterynarz Ciechanów", 52.869645530944744, 20.62307280910547,
                "Płońska 56B, 06-400 Ciechanów", "511818587");

        addVioletMarker(map, "Przychodnia Weterynaryjna Dariusz Pfeifer", 50.72514613690585, 23.253806465483592,
                "Jana Kilińskiego 5, 22-400 Zamość", "846275435");

        /*addVioletMarker(map, "", ,
                "", "");*/

    }

    private void recommendedByUsers (GoogleMap map) {

        addBlueMarker(map, "Zwierzak s.c. Przychodnia weterynaryjna. Usługi weterynaryjne", 53.130622607422, 23.1882754 ,
                "Piastowska 11A, 15-057 Białystok", "857412394");

        addBlueMarker(map, "Przychodnia Weterynaryjna Maria Iwona Rudobielska", 53.15209578059001, 23.080086296297154 ,
                "Michała Pietkiewicza 6D, 15-685 Białystok", "856671148");

        addBlueMarker(map, "Dr n. wet. Małgorzata Taube", 54.42684645276363, 18.483853028835917 ,
                "Feniksa 28, 80-299 Gdańsk", "513131214");

        addBlueMarker(map, "Gdyńska Klinika Weterynaryjna lek. wet. Tomasza Brzeskiego", 54.50847233473258, 18.529873813492255 ,
                "Kielecka 22, 81-303 Gdynia", "586205555");

        addBlueMarker(map, "Przychodnia weterynaryjna Alwet s.c", 54.4664062433334, 16.99821865767184 ,
                "Marszałka Józefa Piłsudskiego 19, 76-200 Słupsk", "597220172");

        addBlueMarker(map, "Przychodnia weterynaryjna Futrzak", 54.60644492291434, 18.234937357671832 ,
                "Ofiar Piaśnicy 4, 84-200 Wejherowo", "503615668");

        addBlueMarker(map, "Trójmiejska Przychodnia Weterynaryjna", 54.35509029458695, 18.57872604232816 ,
                "Bulońska 16/2a, 80-288 Gdańsk", "668240020");

        addBlueMarker(map, "Przychodnia Weterynaryjna \"Na Warszewie\"", 53.46447951646631, 14.543125271164081 ,
                "Duńska 33A, 71-795 Szczecin", "918803807");

        addBlueMarker(map, "Przychodnia Weterynaryjna Vetclinic", 53.43773513154261, 14.495967271164085 ,
                "Grota-Roweckiego 10D, 71-297 Szczecin", "514107055");

        addBlueMarker(map, "Ryś Anna, lek. wet. Lecznica weterynaryjna", 51.93482238411387, 15.486791242328165,
                "Żołnierzy 2 Armii 30, 65-936 Zielona Góra", "795087097");

        addBlueMarker(map, "Ryś Anna, lek. wet. Lecznica weterynaryjna", 51.93482238411387, 15.486791242328165,
                "Żołnierzy 2 Armii 30, 65-936 Zielona Góra", "795087097");

        addBlueMarker(map, "Animal Doctor, gabinet weterynaryjny", 52.755478272556466, 15.248239586507754 ,
                "Witosa 12/H11, 66-400 Gorzów Wielkopolski", "880885006");

        addBlueMarker(map, "Przychodnia Dla Zwierząt CZTERY ŁAPY", 51.77678874101601, 18.084406899999998 ,
                "Żeromskiego 23, 62-800 Kalisz", "625999986");

        addBlueMarker(map, "REHAwet Przychodnia weterynaryjna", 53.14981905277678, 16.720053699999998 ,
                "Stanisława Wyspiańskiego 5/lokal 4, 64-920 Piła", "673533938");

        addBlueMarker(map, "Centrum Zdrowia Małych Zwierząt M. Majka R. Starczewski s.c.", 52.45862251141484, 16.904670042328164 ,
                "Osiedle Władysława Jagiełły 33, 60-694 Poznań", "618243178");

        addBlueMarker(map, "Gabinet Weterynaryjny \"Kobra\"", 52.38691180630137, 16.855464184656334 ,
                "Międzyborska 67, 60-162 Poznań", "616629845");

        addBlueMarker(map, "Zwierzyniec", 51.12632437069835, 16.96949322513307 ,
                "Bulwar Ikara 31B, 54-130 Wrocław", "717568040");

        addBlueMarker(map, "Lecznica dla Zwierząt ERJOT M. Rokosz, A. Januszkiewicz", 50.91169188877489, 15.735142413492246 ,
                "Grunwaldzka 62, 58-506 Jelenia Góra", "757543931");

        addBlueMarker(map, "Jelwet. Przychodnia weterynaryjna. Godoń M., lek. wet", 50.86798596613593, 15.694310228835917 ,
                "Wolności 222, 58-560 Jelenia Góra", "757557104");

        addBlueMarker(map, "Filistowicz Edyta, lek. wet. Przychodnia Weterynaryjna", 50.435412374331385, 16.6613751 ,
                "Lutycka 29, 57-300 Kłodzko", "509696032");

        addBlueMarker(map, "Centrum Weterynaryjne \"AlfaVet\" Lecznica Weterynaryjna", 51.38340090261788, 16.204857215343672 ,
                "Leśna 14, 59-300 Lubin", "790590523");

        addBlueMarker(map, "Trzebnicka Przychodnia Weterynaryjna", 51.31185539223577, 17.06213837116409 ,
                "Tadeusza Kościuszki 18, 55-100 Trzebnica", "717259430");

        addBlueMarker(map, "Przychodnia Weterynaryjna VETKA lek.wet. Paulina Lewicka", 50.85895490668441, 16.4840157 ,
                "Księżnej Jadwigi Śląskiej 1, 58-100 Świdnica", "570076880");

        addBlueMarker(map, "Gabinet weterynaryjny Formica. lek.wet. Paulina Kanczewska", 51.13624560487973, 15.019061073015502 ,
                "Generała Tadeusza Bora-Komorowskiego 4, 59-900 Zgorzelec", "519803600");

        addBlueMarker(map, "Klinika Weterynaryjna Brynów Grupa EDINA", 50.23751299790088, 18.99175296931266 ,
                "Brynowska 25C, 40-585 Katowice", "322517530");

        addBlueMarker(map, "Aleksandra Grzeczny, lek. wet. Lecznica weterynaryjna", 50.085746820287966, 18.2052974 ,
                "Juliusza Słowackiego 50, 47-400 Racibórz", "600691992");

        addBlueMarker(map, "Godziek Michał. Gabinet weterynaryjny", 50.06463895800867, 18.599217153968986 ,
                "Stanisława Małachowskiego 18, 44-251 Rybnik", "324255557");

        addBlueMarker(map, "Gabinet Weterynaryjny SuperVet Anna Sternak", 50.25569239621466, 18.551537315343673,
                "Wiejska 2, 44-153 Smolnica", "788187888");

        addBlueMarker(map, "Gabinet WETKARDIO", 50.29801144647893, 18.798615869312663,
                "teren Fundacji Kardiochirurgii, Wolności 345a, 41-800 Zabrze", "733999849");

        addBlueMarker(map, "Maxvet Dermatolog Weterynaryjny Endoskopia Stomatologia Kardiologia", 50.091107716196326, 19.953791213492245,
                "Szafirowa 8, 31-233 Kraków", "123571958");

        addBlueMarker(map, "Przychodnia Weterynaryjna Vetika. Centrum Zdrowia Zwierząt Towarzyszących i Dzikich", 50.040868095545456, 19.999894799999996 ,
                "Lipska 49, 30-721 Kraków", "511195708");

        addBlueMarker(map, "Oaza. Przychodnia weterynaryjna", 50.07431565701723, 19.932110471164084,
                "Lubelska 16-18/Lok. 10, 30-003 Kraków", "507791189");

        addBlueMarker(map, "Medicavet Przychodnia Weterynaryjna", 50.04027279648534, 19.9255499,
                "Kapelanka 13 C, 30-347 Kraków", "887370413");

        addBlueMarker(map, "Przychodnia dla Zwierząt VET-MEDICA", 50.01536104747148, 22.007716700000003,
                "Graniczna 5B, 35-326 Rzeszów", "172504100");

        addBlueMarker(map, "rzeKotka Gabinet Weterynaryjny", 49.82396313745843, 22.805593271164078,
                "ul.o.św. Jana Pawła II 104, 37-710 Żurawica", "796007290");

        addBlueMarker(map, "Łaczek Paweł, lek. wet. Gabinet weterynaryjny", 50.01404446045984, 20.980849399999997,
                "Chopina 5, 33-100 Tarnów", "889220920");

        addBlueMarker(map, "Przychodnia Weterynaryjna Cztery Łapy", 50.89641160248774, 20.6745519,
                "Jana Nowaka-Jeziorańskiego 140, 25-430 Kielce", "413628850");

        addBlueMarker(map, "Andi. Gabinet małych zwierząt", 50.89053020677717, 20.6660424,
                "Jana Nowaka-Jeziorańskiego 73, 25-432 Kielce", "888471693");

        addBlueMarker(map, "Gabinet Weterynaryjny \"Pazur\"", 53.744701014073755, 20.48527077116409,
                "Rotmistrza Witolda Pileckiego 1/26, 11-041 Olsztyn", "793719016");

        addBlueMarker(map, "Przychodnia Weterynaryjna \"PULSAR\"", 53.73723898997058, 20.506666113495942,
                "Ernsta Wiecherta 27A, 10-691 Olsztyn", "895411020");

        addBlueMarker(map, "Lecznica przy Młynie", 53.708209452636176, 19.957308542328164,
                "Adama Mickiewicza 21C, 14-100 Ostróda", "668028503");

        addBlueMarker(map, "Gabinet Weterynaryjny \"KWIATKOWSCY VET\"", 53.12251423064642, 18.046974071164083,
                "Bałtycka 10A, 85-707 Bydgoszcz", "882500020");

        addBlueMarker(map, "Przychodnia Weterynaryjna CZTERY ŁAPY Marta Bednarek", 52.79207346348496, 18.276829542328166,
                "Jagiellońska 27, 88-111 Inowrocław", "523540835");

        addBlueMarker(map, "Gabinet Weterynaryjny Dr n. wet. Mirosław Krawczyk", 53.03189778248373, 18.601996607317254,
                "Legionów 216B, 87-100 Toruń", "782312823");

        addBlueMarker(map, "PROVET Przychodnia Weterynaryjna", 53.03909435464527, 18.58211874232816,
                "Szosa Chełmińska 217A, 87-100 Toruń", "518724430");

        addBlueMarker(map, "Przychodnia Weterynaryjna Chiron-Wet", 52.652384847026006, 19.074736501851426,
                "Jagiellońska 11, 87-810 Włocławek", "544110008");

        addBlueMarker(map, "Przychodnia Weterynaryjna", 52.23003702802158, 19.3674057,
                "Kardynała Stefana Wyszyńskiego 11, 99-300 Kutno", "503664929");

        addBlueMarker(map, "Przychodnia Weterynaryjna Szpital Zwierząt Egzotycznych OAZA", 52.278649748970864, 20.984104871164085,
                "Potocka 4, 01-652 Warszawa", "224066812");

        addBlueMarker(map, "Przychodnia Weterynaryjna KARAT", 52.30137694308467, 21.05716904232817,
                "Głębocka 13, 03-287 Warszawa", "694165623");

        addBlueMarker(map, "OGONEK Specjalistyczna Przychodnia Weterynaryjna dla Małych Ssaków", 52.23888394036304, 20.936787915343672,
                "Tadeusza Krępowieckiego 10/lok. U-14, 01-456 Warszawa", "226646688");

        addBlueMarker(map, "NiuniaWet", 52.1818511569302, 20.90499741349225,
                "Centralna 25, 05-816 Opacz-Kolonia", "733788252");

        addBlueMarker(map, "Docvet. Gabinet weterynaryjny. Sobieraj K., lek. wet.", 52.1731269785405, 20.806666584655762,
                "3 Maja 23, 05-800 Pruszków", "227584674");

        addBlueMarker(map, "Radomskie Centrum Małych Zwierząt", 51.420197443291386, 21.164342728835912,
                "Bolesława Chrobrego 46/Lok.2, 26-615 Radom", "666255256");

    }


    private void recommendedMarkers(GoogleMap map) {

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna Morska")
                .position(new LatLng (54.52780127494107, 18.502941727210477))
                .snippet("1/2, Jacka Kaczmarskiego 1, 81-230 Gdynia\n587108192"));

        map.addMarker(new MarkerOptions()
                .title("Gabinet Weterynaryjny Zoo-Wet")
                .position(new LatLng (54.49528818677501, 18.551192192556865))
                .snippet("Bohaterów Starówki Warszawskiej 6/14, 81-455 Gdynia\n790732357"));

        map.addMarker(new MarkerOptions()
                .title("VETMEDIC - Gabinet weterynaryjny")
                .position(new LatLng (53.45486395972331, 14.554978199999995))
                .snippet("Przyjaciół Żołnierza 80a, 71-670 Szczecin\n918294627"));

        map.addMarker(new MarkerOptions()
                .title("Gabinet weterynaryjny Dino-Vet dr n. wet. Anna Kołodziejska-Sawerska")
                .position(new LatLng (53.08317688677131, 18.227671))
                .snippet("Toruńska 6, 86-050 Solec Kujawski\n724842079"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna Sowa Egzotica")
                .position(new LatLng (53.11701036400971, 18.004664871164376))
                .snippet("Romualda Traugutta 11, 85-122 Bydgoszcz\n523070747"));

        map.addMarker(new MarkerOptions()
                .title("Gabinet weterynaryjny lek. wet Dariusz Filipiński")
                .position(new LatLng (52.43928104689362, 16.939457200000003))
                .snippet("Osiedle Wichrowe Wzgórze 103, 61-699 Poznań\n618232951"));

        map.addMarker(new MarkerOptions()
                .title("Weterynaryjne centrum Garfild")
                .position(new LatLng (52.41503157853447, 16.889910999999998))
                .snippet("Jana Henryka Dąbrowskiego 130C, 60-576 Poznań\n784355358"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia weterynaryjna Neo Vet lek. wet. Jakub Pernak")
                .position(new LatLng (52.38521096374098, 16.887665800000004))
                .snippet("Głogowska 164, 60-126 Poznań\n618659034"));

        map.addMarker(new MarkerOptions()
                .title("Arka Noego - Gabinet Weterynaryjny")
                .position(new LatLng (52.27720335113131, 17.009484115342467))
                .snippet("Szafirowa 52, 62-023 Kamionki\n602464664"));

        map.addMarker(new MarkerOptions()
                .title("Medicavet Specjalistyczna Przychodnia Weterynaryjna")
                .position(new LatLng (52.19866967170752, 20.9787087))
                .snippet("Racławicka 146, 02-117 Warszawa\n224032255"));

        map.addMarker(new MarkerOptions()
                .title("Klinika małych zwierząt")
                .position(new LatLng (52.1626156749537, 21.048833648343347))
                .snippet("Klinika małych zwierząt, Nowoursynowska 159c, 02-776 Warszawa\n"));

        map.addMarker(new MarkerOptions()
                .title("EDINA Przychodnia Weterynaryjna PulsVet")
                .position(new LatLng (52.146176326277036, 21.018405857671237))
                .snippet("Puławska 403, 02-801 Warszawa\n226493117"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna Dr.Wet")
                .position(new LatLng (52.12928261120121, 21.0729424))
                .snippet("Jerzego Zaruby 9/lok. E2, 02-796 Warszawa\n226432507"));

        map.addMarker(new MarkerOptions()
                .title("Zwierzętarnia")
                .position(new LatLng (51.75967209640525, 19.43318460979451))
                .snippet("aleja Włókniarzy 234, 90-556 Łódź\n660950575"));

        map.addMarker(new MarkerOptions()
                .title("SKVet Klinika Weterynaryjna")
                .position(new LatLng (51.16511735657117, 16.898650242328763))
                .snippet("Stabłowicka 109B, 54-062 Wrocław\n791998999"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna Rex M. Orzelski D. Różańska sp.j.")
                .position(new LatLng (51.24108208510563, 22.5173418))
                .snippet("Leonarda 3A, 20-625 Lublin\n814430313"));

        map.addMarker(new MarkerOptions()
                .title("Oddział Drobnych Ssaków Gabinet Weterynaryjny Lublin")
                .position(new LatLng (51.24109787583792, 22.542037258275023))
                .snippet("Głęboka 30H, 20-400 Lublin\n535321417"));

        map.addMarker(new MarkerOptions()
                .title("Gabinet Weterynaryjny Kolonia Gosławicka")
                .position(new LatLng (50.66358421449389, 17.986526899999998))
                .snippet("Grudzicka 47, 46-020 Opole\n791232372"));

        map.addMarker(new MarkerOptions()
                .title("Gabinet weterynaryjny Synergia")
                .position(new LatLng (50.38399212273186, 18.882586842328767))
                .snippet("Strzelców Bytomskich 129, 41-902 Bytom\n884840636"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna REPTILIO Grupa EDINA")
                .position(new LatLng (50.302720662786676, 19.019765542328766))
                .snippet("Olimpijska 6, 41-100 Siemianowice Śląskie\n327824082"));

        map.addMarker(new MarkerOptions()
                .title("Przychodnia Weterynaryjna Salamandra")
                .position(new LatLng (50.073603401998305, 19.918123455821913))
                .snippet("Nowowiejska 3, 30-052 Kraków\n606426200"));

        map.addMarker(new MarkerOptions()
                .title("Ośrodek Rehabilitacji Mysikrólik - Na Pomoc Dzikim Zwierzętom")
                .position(new LatLng (49.82309546098227, 19.11860969338451))
                .snippet("Admiralska 10, 43-300 Bielsko-Biała\n506918902"));
    }


    @Override
    public boolean onMarkerClick(@NonNull Marker marker) {
        return false;
    }
}

