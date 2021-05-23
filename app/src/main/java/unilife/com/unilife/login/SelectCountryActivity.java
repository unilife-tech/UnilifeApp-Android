package unilife.com.unilife.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import butterknife.BindView;
import unilife.com.unilife.R;
import unilife.com.unilife.updated.BaseActivity;

public class SelectCountryActivity extends BaseActivity {

    HashMap<String, String> hashMap = new HashMap<>();

    @BindView(R.id.listView)
    ListView listView;
    @BindView(R.id.etSearch)
    EditText etSearch;

    protected void onViewReady(Bundle savedInstanceState, Intent intent) {
        super.onViewReady(savedInstanceState, intent);
        setCountries();

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.addAll(hashMap.keySet());
        Collections.sort(arrayList);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int s, int i1, int i2) {

                if (charSequence.toString().trim().length() > 0) {
                    ArrayList<String> arrayListTemp = new ArrayList<>();

                    for (int i = 0; i < arrayList.size(); i++) {
                        if (arrayList.get(i).toLowerCase().contains(charSequence.toString().toLowerCase().trim()))
                            arrayListTemp.add(arrayList.get(i));
                    }

                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayListTemp);
                    listView.setAdapter(arrayAdapter);

                } else {
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, arrayList);
                    listView.setAdapter(arrayAdapter);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                sharePref.setCountryName(listView.getItemAtPosition(i).toString());
                sharePref.setCountryCode(hashMap.get(listView.getItemAtPosition(i).toString()));
                finish();
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_select_country;
    }

    private void setCountries() {
        hashMap.put("Afghanistan", " +93");
        hashMap.put("Albania", " +355");
        hashMap.put("Algeria", " +213");
        hashMap.put("American Samoa", " +1-684");
        hashMap.put("Andorra "," +376");
        hashMap.put("Anguilla ", "+1-264");
        hashMap.put("Antarctica", " +672");
        hashMap.put("Antigua and Barbuda", " +1-268");
        hashMap.put("Argentina ", " +54");
        hashMap.put("Armenia", " +374");
        hashMap.put("Angola ", " +297");
        hashMap.put("Aruba ", " +61");
        hashMap.put("Australia", " +43");
        hashMap.put("Austria", " +994");
        hashMap.put("Azerbaijan or Azerbaidjan (Former Azerbaijan Soviet Socialist Republic)", " ");
        hashMap.put("Bahamas, Commonwealth of The", " +1-242");
        hashMap.put("Bahrain, Kingdom of (Former Dilmun)", " +973");
        hashMap.put("Bangladesh (Former East Pakistan)", " +880");
        hashMap.put("Barbados", " +1-246");
        hashMap.put("Belarus (Former Belorussian [Byelorussian] Soviet Socialist Republic)", " +375");
        hashMap.put("Belgium", " +32");
        hashMap.put("Belize (Former British Honduras) ", " +501");
        hashMap.put("Benin (Former Dahomey)", " +229");
        hashMap.put("Bermuda", " +1-441");
        hashMap.put("Bhutan, Kingdom of", " +975");
        hashMap.put("Bolivia", " +591");
        hashMap.put("Bosnia and Herzegovina", " +387");
        hashMap.put("Botswana (Former Bechuanaland)", " +267");
        hashMap.put("Bouvet Island (Territory of Norway)", " ");
        hashMap.put("Brazil ", " +55");
        hashMap.put("British Indian Ocean Territory (BIOT)", " ");
        hashMap.put("Brunei (Negara Brunei Darussalam)", " +673");
        hashMap.put("Bulgaria", " +359");
        hashMap.put("Burkina Faso (Former Upper Volta)", " +226");
        hashMap.put("Burundi (Former Urundi) ", " +257");
        hashMap.put("Cambodia, Kingdom of (Former Khmer Republic, Kampuchea Republic)", " +855");
        hashMap.put("Cameroon (Former French Cameroon)", " +237");
        hashMap.put("Canada ", " +1");
        hashMap.put("Cape Verde ", " +238");
        hashMap.put("Cayman Islands", " +1-345");
        hashMap.put("Central African Republic", " +236");
        hashMap.put("Chad", " +235");
        hashMap.put("Chile", " +56");
        hashMap.put("China", " +86");
        hashMap.put("Christmas Island", " +53");
        hashMap.put("Cocos (Keeling) Islands ", " +61");
        hashMap.put("Colombia ", " +57");
        hashMap.put("Comoros, Union of the", " +269");
        hashMap.put("Congo, Democratic Republic of the (Former Zaire)", " +243");
        hashMap.put("Congo, Republic of the", " +242");
        hashMap.put("Cook Islands (Former Harvey Islands) ", " +682");
        hashMap.put("Costa Rica ", " +506");
        hashMap.put("Cote D'Ivoire (Former Ivory Coast)", " +225");
        hashMap.put("Croatia (Hrvatska)", " +385");
        hashMap.put("Cuba", " +53");
        hashMap.put("Cyprus", " +357");
        hashMap.put("Czech Republic", " +420");
        hashMap.put("Czechoslavakia (Former) See CZ Czech Republic or Slovakia ", " ");
        hashMap.put("Denmark ", " +45");
        hashMap.put("Djibouti (Former French Territory of the Afars and Issas, French Somaliland)", " +253");
        hashMap.put("Dominica + ", " +1-767");
        hashMap.put("Dominican Republic", " +1-809");
        hashMap.put("East Timor (Former Portuguese Timor) ", " +670");
        hashMap.put("Ecuador", " +593 ");
        hashMap.put("Egypt (Former United Arab Republic - with Syria)", " +20");
        hashMap.put("El Salvador", " +503");
        hashMap.put("Equatorial Guinea (Former Spanish Guinea)", " +240");
        hashMap.put("Eritrea (Former Eritrea Autonomous Region in Ethiopia)", " +291");
        hashMap.put("Estonia (Former Estonian Soviet Socialist Republic)", " +372");
        hashMap.put("Ethiopia (Former Abyssinia, Italian East Africa)", " +251");
        hashMap.put("Falkland Islands (Islas Malvinas)", " +500");
        hashMap.put("Faroe Islands", " +298");
        hashMap.put("Fiji", " +679");
        hashMap.put("Finland ", " +358");
        hashMap.put("France ", " +33");
        hashMap.put("French Guiana or French Guyana", " +594");
        hashMap.put("French Polynesia (Former French Colony of Oceania)", " +689");
        hashMap.put("French Southern Territories and Antarctic Lands", " ");
        hashMap.put("Gabon (Gabonese Republic)", " +241");
        hashMap.put("Gambia, The", " +220");
        hashMap.put("Georgia (Former Georgian Soviet Socialist Republic)", " +995");
        hashMap.put("Germany", " +49");
        hashMap.put("Ghana (Former Gold Coast)", " +233");
        hashMap.put("Gibraltar", " +350");
        hashMap.put("Great Britain (United Kingdom)", " ");
        hashMap.put("Greece ", " +30");
        hashMap.put("Greenland", " +299");
        hashMap.put("Grenada", " +1-473");
        hashMap.put("Guadeloupe", " +590");
        hashMap.put("Guam", " +1-671");
        hashMap.put("Guatemala", " +502");
        hashMap.put("Guinea (Former French Guinea) ", " +224");
        hashMap.put("Guinea-Bissau (Former Portuguese Guinea) ", " +245");
        hashMap.put("Guyana (Former British Guiana)", " +592");
        hashMap.put("Haiti", " +509");
        hashMap.put("Heard Island and McDonald Islands (Territory of Australia)", " ");
        hashMap.put("Holy See (Vatican City State) ", " ");
        hashMap.put("Honduras", " +504");
        hashMap.put("Hong Kong", " +852");
        hashMap.put("Hungary", " +36");
        hashMap.put("Iceland", " +354");
        hashMap.put("India", " +91");
        hashMap.put("Indonesia (Former Netherlands East Indies; Dutch East Indies) ", " +62");
        hashMap.put("Iran, Islamic Republic of", " +98");
        hashMap.put("Iraq", " +964");
        hashMap.put("Ireland ", " +353");
        hashMap.put("Israel ", " +972");
        hashMap.put("Italy", " +39");
        hashMap.put("Jamaica ", " +1-876");
        hashMap.put("Japan", " +81");
        hashMap.put("Jordan (Former Transjordan) ", " +962");
        hashMap.put("Kazakstan or Kazakhstan (Former Kazakh Soviet Socialist Republic)", " +7");
        hashMap.put("Kenya (Former British East Africa)", " +254");
        hashMap.put("Kiribati (Pronounced keer-ree-bahss) (Former Gilbert Islands) ", " +686");
        hashMap.put("Korea, Democratic People's Republic of (North Korea)", " +850");
        hashMap.put("Korea, Republic of (South Korea) ", " +82");
        hashMap.put("Kuwait ", " +965");
        hashMap.put("Kyrgyzstan (Kyrgyz Republic) (Former Kirghiz Soviet Socialist Republic)", " +996");
        hashMap.put("Lao People's Democratic Republic (Laos)", " +856");
        hashMap.put("Latvia (Former Latvian Soviet Socialist Republic) ", " +371");
        hashMap.put("Lebanon", " +961");
        hashMap.put("Lesotho (Former Basutoland) ", " +266");
        hashMap.put("Liberia", " +231");
        hashMap.put("Libya (Libyan Arab Jamahiriya)", " +218");
        hashMap.put("Liechtenstein", " +423");
        hashMap.put("Lithuania (Former Lithuanian Soviet Socialist Republic)", " +370");
        hashMap.put("Luxembourg ", " +352");
        hashMap.put("Macau", " +853");
        hashMap.put("Macedonia, The Former Yugoslav Republic of", " +389");
        hashMap.put("Madagascar (Former Malagasy Republic)", " +261");
        hashMap.put("Malawi (Former British Central African Protectorate, Nyasaland) ", " +265");
        hashMap.put("Malaysia", " +60");
        hashMap.put("Maldives", " +960");
        hashMap.put("Mali (Former French Sudan and Sudanese Republic)", " +223");
        hashMap.put("Malta", " +356");
        hashMap.put("Marshall Islands (Former Marshall Islands District - Trust Territory of the Pacific Islands)", " +692");
        hashMap.put("Martinique (French) ", " +596");
        hashMap.put("Mauritania ", " +222");
        hashMap.put("Mauritius", " +230");
        hashMap.put("Mayotte (Territorial Collectivity of Mayotte)", " +269");
        hashMap.put("Mexico ", " +52");
        hashMap.put("Micronesia, Federated States of (Former Ponape, Truk, and Yap Districts - Trust Territory of the Pacific Isla", " +691");
        hashMap.put("Moldova, Republic of", " +373");
        hashMap.put("Monaco, Principality of ", " +377");
        hashMap.put("Mongolia (Former Outer Mongolia) ", " +976");
        hashMap.put("Montserrat ", " +1-664");
        hashMap.put("Morocco ", " +212");
        hashMap.put("Mozambique (Former Portuguese East Africa)", " +258");
        hashMap.put("Myanmar, Union of (Former Burma) ", " +95");
        hashMap.put("Namibia (Former German Southwest Africa, South-West Africa)", " +264");
        hashMap.put("Nauru (Former Pleasant Island)", " +674");
        hashMap.put("Nepal", " +977");
        hashMap.put("Netherlands ", " +31");
        hashMap.put("Netherlands Antilles (Former Curacao and Dependencies)", " +599");
        hashMap.put("New Caledonia", " +687");
        hashMap.put("New Zealand (Aotearoa)", " +64");
        hashMap.put("Nicaragua", " +505");
        hashMap.put("Niger", " +227");
        hashMap.put("Nigeria ", " +234");
        hashMap.put("Niue (Former Savage Island) ", " +683");
        hashMap.put("Norfolk Island", " +672");
        hashMap.put("Northern Mariana Islands (Former Mariana Islands District - Trust Territory of the Pacific Islands)", " +1-670");
        hashMap.put("Norway ", " +47");
        hashMap.put("Oman, Sultanate of (Former Muscat and Oman)", " +968");
        hashMap.put("Pakistan (Former West Pakistan)", " +92");
        hashMap.put("Palau (Former Palau District - Trust Terriroty of the Pacific Islands) ", " +680");
        hashMap.put("Palestinian State (Proposed)", " +970");
        hashMap.put("Panama ", " +507");
        hashMap.put("Papua New Guinea (Former Territory of Papua and New Guinea)", " +675");
        hashMap.put("Paraguay", " +595");
        hashMap.put("Peru ", " +51");
        hashMap.put("Philippines", " +63");
        hashMap.put("Pitcairn Island ", "+64");
        hashMap.put("Poland ", " +48");
        hashMap.put("Portugal +1 ", " +351");
        hashMap.put("Puerto Rico", " +1-787");
        hashMap.put("Qatar, State of ", " +974 ");
        hashMap.put("Reunion (French) (Former Bourbon Island) ", " +262");
        hashMap.put("RomaniaRat ", " +40");
        hashMap.put("Russia - USSR (Former Russian Empire, Union of Soviet Socialist Republics, Russian Soviet Federative Socialis)", "");
        hashMap.put("Russian Federation", " +7");
        hashMap.put("Rwanda (Rwandese Republic) (Former Ruanda)", " +250");
        hashMap.put("Saint Helena", " +290");
        hashMap.put("Saint Kitts and Nevis (Former Federation of Saint Christopher and Nevis)", " +1-869");
        hashMap.put("Saint Lucia", " +1-758");
        hashMap.put("Saint Pierre and Miquelon", " +508");
        hashMap.put("Saint Vincent and the Grenadines ", " +1-784");
        hashMap.put("Samoa (Former Western Samoa)", " +685");
        hashMap.put("San Marino ", " +378");
        hashMap.put("Sao Tome and Principe", " +239");
        hashMap.put("Saudi Arabia ", " +966");
        hashMap.put("Serbia, Republic of ", " ");
        hashMap.put("Senegal", " +221");
        hashMap.put("Seychelles ", " +248");
        hashMap.put("Sierra Leone ", " +232");
        hashMap.put("Singapore", " +65");
        hashMap.put("Slovakia", " +421");
        hashMap.put("Slovenia", " +386");
        hashMap.put("Solomon Islands (Former British Solomon Islands)", " +677");
        hashMap.put("Somalia (Former Somali Republic, Somali Democratic Republic)", " +252");
        hashMap.put("South Africa (Former Union of South Africa)", " +27");
        hashMap.put("South Georgia and the South Sandwich Islands ", " ");
        hashMap.put("Spain", " +34");
        hashMap.put("Sri Lanka (Former Serendib, Ceylon)", " +94");
        hashMap.put("Sudan (Former Anglo-Egyptian Sudan)", " +249");
        hashMap.put("Suriname (Former Netherlands Guiana, Dutch Guiana)", " +597");
        hashMap.put("Svalbard (Spitzbergen) and Jan Mayen Islands ", " ");
        hashMap.put("Swaziland, Kingdom of", " +268");
        hashMap.put("Sweden ", " +46");
        hashMap.put("Switzerland", " +41");
        hashMap.put("Syria (Syrian Arab Republic) (Former United Arab Republic - with Egypt)", " +963");
        hashMap.put("Taiwan (Former Formosa) ", " +886");
        hashMap.put("Tajikistan (Former Tajik Soviet Socialist Republic)", " +992");
        hashMap.put("Tanzania, United Republic of (Former United Republic of Tanganyika and Zanzibar) ", " +255");
        hashMap.put("Thailand (Former Siam)", " +66");
        hashMap.put("Tokelau", " +690");
        hashMap.put("Tonga, Kingdom of (Former Friendly Islands)", " +676");
        hashMap.put("Trinidad and Tobago ", " +1-868");
        hashMap.put("Tromelin Island ", " ");
        hashMap.put("Tunisia", " +216");
        hashMap.put("Turkey", " +90");
        hashMap.put("Turkmenistan (Former Turkmen Soviet Socialist Republic)", " +993");
        hashMap.put("Turks and Caicos Islands", " +1-649");
        hashMap.put("Tuvalu (Former Ellice Islands)", " +688");
        hashMap.put("Uganda, Republic of ", " +256");
        hashMap.put("Ukraine (Former Ukrainian National Republic, Ukrainian State, Ukrainian Soviet Socialist Republic)", " +380");
        hashMap.put("United Arab Emirates (UAE) (Former Trucial Oman, Trucial States)); ", " +971");
        hashMap.put("United Kingdom (Great Britain / UK)", " +44");
        hashMap.put("United States", " +1");
        hashMap.put("United States Minor Outlying Islands ", "+246");
        hashMap.put("Uruguay, Oriental Republic of (Former Banda Oriental, Cisplatine Province) ", " +598");
        hashMap.put("Uzbekistan (Former UZbek Soviet Socialist Republic)", " +998");
        hashMap.put("Vanuatu (Former New Hebrides) ", " +678");
        hashMap.put("Vatican City State (Holy See) ", " +418");
        hashMap.put("Venezuela", " +58");
        hashMap.put("Vietnam ", " +84");
        hashMap.put("Virgin Islands, British ", " +1-284");
        hashMap.put("Virgin Islands, United States (Former Danish West Indies) ", " +1-340");
        hashMap.put("Wallis and Futuna Islands", " +681");
        hashMap.put("Western Sahara (Former Spanish Sahara)", "+212");
        hashMap.put("Yemen", " +967");
        hashMap.put("Yugoslavia  h", "+38");
        hashMap.put("Zaire (Former Congo Free State, Belgian Congo, Congo/Leopoldville, Congo/Kinshasa, Zaire) Now CD - Congo, Deme", " ");
        hashMap.put("Zambia, Republic of (Former Northern Rhodesia) ", " +260");
        hashMap.put("Zimbabwe, Republic of (Former Southern Rhodesia, Rhodesia)", " +263");
    }

    public void goBack(View view) {
        finish();
    }
}