package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.Category
import com.example.data.model.EtiquetteItem
import com.example.data.model.Place
import com.example.data.model.PlaceGallery
import com.example.data.model.Trivia
import com.example.data.model.WeatherData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ExploreRepository(context: Context? = null) {

    private val prefs: SharedPreferences? = context?.getSharedPreferences("explore_nanded_prefs", Context.MODE_PRIVATE)

    private val _favorites = MutableStateFlow<Set<String>>(loadSavedFavorites())
    val favorites: StateFlow<Set<String>> = _favorites.asStateFlow()

    private val _recentlyViewed = MutableStateFlow<List<String>>(loadRecentlyViewed())
    val recentlyViewed: StateFlow<List<String>> = _recentlyViewed.asStateFlow()

    val weatherData = WeatherData(
        location = "Nanded, Maharashtra",
        temperature = "28°C",
        condition = "Sunny",
        godavariFlow = "Calm",
        airQuality = "Good (AQI 42)",
        sunsetTime = "6:14 PM"
    )

    val categories: List<Category> = listOf(
        Category(
            id = "historical",
            name = "Historical Places",
            nativeName = "ऐतिहासिक स्थळे",
            iconName = "castle",
            description = "Discover 800+ years of sacred history, Rashtrakuta empires, and Sikh heritage along the Godavari.",
            count = 18,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCNgkXJLjKLvp7ojkW8LKUQAgRDdfV0Z0ICVPbQsj3B2wRxoEOnsdUlH9DVIu7f-54F5isOSURwGqGEcvpGzI_myVqMwnTnLeeNyFVICHUnYUSlHKVF95SqItXsOKYdihRxxYf5OSa1sUvzR1hKEhLYDGNQV2UFziTqq3rutOSlH96bNQWvo_vgr5nmkqXVgS7NLFd9jjIqnhjkt88DAUallK9JBQRIlg_4jFs_lSy4gKcrcHPp_NTuAw",
            filterTags = listOf("All Places", "Forts & Ruins", "Monuments", "River Ghats", "Museums")
        ),
        Category(
            id = "spiritual",
            name = "Temples & Gurdwaras",
            nativeName = "धार्मिक व पावन स्थळे",
            iconName = "temple_hindu",
            description = "Revered takhts, ancient Hemadpanti shrines, and serene river ghats of spiritual peace.",
            count = 24,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBXkA2MZaqziwot7IV2vNQ_n82WTYUKu6QaTocaJmZ4M4v6xDtp9DPiYogZegZJfidheMTccIayOs5tXKAECTpC5BUHFz9mIWu6aB34Qop74fGuYwQGO1DyC7HoJmKJaTcoEKBqHXe1dhezI5EifMXIjAfQVjJ52jRnRGRLXbK4Q2Yr_EUVXYNcxjsixAdiivNVzZRS1bg4uW5-nrBzGWOpYN5S1zZ5h7ajoeh9-YnuOty4dnResVk8EQ",
            filterTags = listOf("All Places", "Gurdwaras", "Hemadpanti", "Shaktipeeth", "River Ghats")
        ),
        Category(
            id = "cafes",
            name = "Cafes & Hangouts",
            nativeName = "कॅफे व कट्टा",
            iconName = "coffee",
            description = "Chic brew bars, cold artisanal brews, rooftop hangouts, and student favorite cafes.",
            count = 14,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDFGQd4rhJAiD7oxo6E4F9w6sVO411PQ5rfe1gGZH8Pmuskkc-gvJhNeC5diFwtrUqLP_wvsz_4VdOpcsyq1brsdPNhi7NkwNySOQvbtvJR0U0UMxBn3lLvwlk5V8d3KfsPyZbNJts7ytc9gjHbuT6NBZLTLoF_cxIK4X-T7fhn-vkls4tlAs_-tzrN7taL6SdvQuRCDdXisleeM3F1lrI5iSqBKb6ShqGtqPrOvSu5LwzfwIyyqpOrUw",
            filterTags = listOf("All Places", "Artisan Coffee", "Rooftops", "Desserts", "Quick Bites")
        ),
        Category(
            id = "restaurants",
            name = "Taste of Marathwada",
            nativeName = "मराठवाडी खाद्यसंस्कृती",
            iconName = "restaurant",
            description = "Authentic spicy Marathwada thali, fiery thecha, shev bhaji, and heritage sweets.",
            count = 22,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDUZDePOrSx63E0XJN62hdMgFipEImIGrOUs54tsuakJJAbDT9iRHAvI1SD79pEcGQu4MR_WKXTzUa2C0GCBooKsCboHTcwdhXJCooRR4lV5MScPSu41Fki51Cy7g52WsVYAtT8IfOH71YpRdyewza53Qwkmro4tJonpz8ixV-1h04sbAGhd9qZR5Gh9n1KqvtlQd_NZEgZ5DsP0oqqrt2Z7UFsTC8d0_1aqk-xl9BItp3AEgjjFmgQcw",
            filterTags = listOf("All Places", "Thali Specials", "Spicy Curries", "Pure Veg", "Street Food")
        ),
        Category(
            id = "parks",
            name = "Parks & Nature",
            nativeName = "उद्याने व निसर्ग",
            iconName = "park",
            description = "Godavari riverside promenade, scenic botanical gardens, and sunset view parks.",
            count = 11,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAPlfRS1_Nc2C9Zfzn1zoeje3iycS-6OlnROrMIE89b2tBfzXVbjZRBV3flLnXPuP_HcHizGoCctZmTdXu7zPSTTJ_2K8TgyvboMYIpiZPoMuU4dRlDqCH7SHFuH4V87xmo9Y0Vur17_IE7oJo_-zVm7bzcqd6Fr0B6H9G6m54Ph8AzyUNyslcSw2fu23GCKwttCxjgn7gg724gOqS7yZbbEWwJvTe3sUc5jiehKLtC7ihE7qJCuQ4hfg",
            filterTags = listOf("All Places", "Riverwalk", "Gardens", "Boating", "Kids Play")
        ),
        Category(
            id = "hidden-gems",
            name = "Hidden Gems",
            nativeName = "अनोखी व दुर्लक्षित स्थळे",
            iconName = "diamond",
            description = "Unexplored ancient stepwells, scenic backwaters, and offbeat medieval ruins.",
            count = 9,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBr9cvpWbUP7ZMu3r5UpeuDdrXdF1Wxjboaf_uE-WT_0hgUDqEDRSg1SH7LyfE4zTJILkYbAJFNiNAty-w6MTJAccBh7IRZSUC1zou5q2rjQ0uDDEbs6rqVW_bJrd9sqL2KfhH0owmcKOtjkM4_EFldeSLb56bbQIvEl-sSImav5WJALdhwfibl1cIp912wkXDGcgU2983N7z64jRG6dmK-P4BkyNbd_qkrWANjGpvLhDpLWtX9-VoAGQ",
            filterTags = listOf("All Places", "Ancient Inscriptions", "Moats", "Rock Art", "Quiet Spots")
        ),
        Category(
            id = "weekend-getaways",
            name = "Weekend Getaways",
            nativeName = "विकेंड सहली",
            iconName = "explore",
            description = "Day tours, hill shrines, and historic fortresses within 2 hours of Nanded.",
            count = 15,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDhh3iMaVHsUHJU9CuCXmEjjjDuKxqhqdq8ddJJ8m-4wCxdsuCBYb3Wg_stmLZcUoZD0ANgepvZab8oERMxh_BmN-yx2-1XkV-ZFpOrpTk6A4mCa8BtmKrZobBcSuwBphKqmNGgnY0MjMfWp2uww4XY2VkcLUBxzxOuLLFyxW0ulqXLmCZL4Bgmck7s6_BWXCHKIvp9fDvw9dOcbYa5Oz7n-nS3-qtHA8Zq4f19EV_C0-dUhtC7Q-A4PA",
            filterTags = listOf("All Places", "Hill Shrines", "Day Treks", "Waterfalls", "Forest Drives")
        ),
        Category(
            id = "shopping",
            name = "Local Markets & Bazaar",
            nativeName = "स्थानिक बाजारपेठ",
            iconName = "shopping_bag",
            description = "Traditional brass handicrafts, holy memorabilia, spiced papads, and textiles.",
            count = 12,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAITRBLf-ERxEbPQfasLxP7GNW6oVVqsZzO4hj5zPQIw-5gz2A8Zc4lgz0InCkd1VJzAVuGHejhjpgbb67gBD41usavekTxqlFiJupiHd4x0o-IEGk9koIEtEsgsf5yhfgC4G-AgMz37PuViUSmUXNm0HueY5P0Fn6ergzfOwQB2bfL-1Wxmr5DfPUtiBDO3rEVtcHP__ozq8WraZgWK90LTO2q523IiJyF2x1LsIBMda1aJuZEMpQijQ",
            filterTags = listOf("All Places", "Brassware", "Holy Souvenirs", "Cloth Market", "Spices")
        )
    )

    val places: List<Place> = listOf(
        Place(
            id = "hazur-sahib",
            name = "Takht Sachkhand Sri Hazur Abchalnagar Sahib",
            nativeName = "तख्त सचखंड श्री हजूर साहिब • ਤਖ਼ਤ ਸੱਚਖੰਡ ਸ੍ਰੀ ਹਜ਼ੂਰ ਅਬਚਲਨਗਰ ਸਾਹਿਬ",
            tagline = "Sachkhand Sri Hazur Abchalnagar Sahib • Central Nanded",
            categoryId = "historical",
            subCategory = "Sacred Landmark",
            rating = 4.9,
            reviewCount = 18420,
            distance = "1.2 km away",
            distanceKm = 1.2,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBzgNszFVoCpxi3ARG_I23XDKqa99QhIf7tMd6O78nni1tUwxOqxfFLKoeDTSiKkffagP4W3I8hkzYfnQsB4IabWx0Upxt-Wk9UHgkzCc99s3Uw4BuB0klJ2r3TkK9na08Ad4yt_BRXTDoIJ60DA1QyckwCUD3RCXbSNcdX4dEPYWlDhl9Df9uyc1z8SGkZ-YuGiMFcUPcbVpq5QmxYb7gYdcgi--yC9ejn-CO1FqMyafY4NVy46vZcqQ",
            gallery = listOf(
                PlaceGallery("g1", "Golden Dome", "https://lh3.googleusercontent.com/aida-public/AB6AXuDDgtDgdv5DT-yWLH9xwlPfHVu91paFo8z9o5tj4c3HrfL3G-XYGjYea-KUDwyEUVIYKUGMC9-ct9z5rxnSDofadbX1T90In1fYOJaAzKShmopH1OK7_Mzr3LIGXTwhnNO2O713XXcoZMYprm5Y7fb7CPUR1in5eLZaI_TkTukN5DR9mSOwt0-TAz_CvFeuhtJZV9uduSUHvM5lOkc1shS1wfymAFYdQGE_PVFw-IZs5xZvwabSWdkLWw", "Detailed filigree craftsmanship on the sanctum dome"),
                PlaceGallery("g2", "Guru Ka Langar", "https://lh3.googleusercontent.com/aida-public/AB6AXuDvRJMViCeku8JPF5nrQEGUQA8FBSVCg0u-d1ArkvWW8kDs1VmF3Oo-UPmI3wv7U7ErsXAe-6besU8yr8HlGZHoudgWdG0dMV4rGgxASRwfKk82uphPQKCcS4-nUKGjfSilHkYY3DfK7gZFjHa4dSuscR9iKAmkXyY28fgG0GTaARZ6Psxr-bwbU3f69b0Mr4T4J-cnWImWrmGYJi8OUbXK7BI4Z2QUX7JGuSllQH80pFXmpTrSKMbHmQ", "Continuous hot langar meals served to thousands daily"),
                PlaceGallery("g3", "Bhavan & Ghats", "https://lh3.googleusercontent.com/aida-public/AB6AXuAivRs3M08Jpb3NvvitRX5gBWpVECZwZB3wTQ3E1KiSfJHhN0GQtDEJFKgusScPGJhXd7ueOuCb6p0aWm-B9Kp79yj5BuICHuFKfTP2rwx9GOSsrMd7Sls1eJfI6bQZ0VEKRozlm3CPDmGqAEr3yCcGPpFKkMDXlCMVOy51RI6vH7DKMMZPHzM6OxVh952B6RutsncJEKQ7V5Bhvdy2PjLv-ZNdgGzaw2pAm46eeNabyaR__HWnBOqfYw", "Godavari river ghat at dawn near Hazur Sahib"),
                PlaceGallery("g4", "Sacred Shastras", "https://lh3.googleusercontent.com/aida-public/AB6AXuCj-QVFhLaFfzrJFCZcBfnDg67VRzJFOlZck2vVDtr1KOH-HLdmXWZEbqKIs6nUntl0bEo2GQZOFqqMvsZJHB5ELc9QwNolY8GFrCx7-OzDiValUMozMtREySr76zPyaY5Ouer0SY_naAEuPJFmhspROviPmGc5mQk8Mm8M_fUsvrSaxITGcw6JYPxDr49CWN3e-1I2y4Ea4Rht5kADkvZwzOU95rchjLTU8tDCb57Z8SRdH4mG8xTdzQ", "Historical sacred weapons preserved inside the sanctum")
            ),
            address = "Station Road, Nanded, Maharashtra 431601",
            timings = "Open 24 Hours • Langar Seva Ongoing",
            scheduleDetails = "Rehras Sahib & Sandhya Aarti: 6:30 PM Daily • Amritvela Nitnem starts at 2:30 AM every morning",
            entryFee = "Free Entry",
            description = "Hazur Sahib is one of the five takhts in Sikhism, marking the revered site where the tenth guru, Guru Gobind Singh Ji, left his earthly abode in 1708. In keeping with his proclamation, the Guru Granth Sahib was enthroned as the eternal Guru here.\n\nThe inner sanctum, known as Angitha Sahib, was constructed by Maharaja Ranjit Singh between 1832 and 1837, showcasing magnificent copper-gilded arches, intricate inlay lapis lazuli, and timeless Deccan Sikh architecture.",
            historyText = "Takht Sachkhand Sri Hazur Abchalnagar Sahib marks where Guru Gobind Singh Ji spent his final months and consecrated the Guru Granth Sahib before attaining Joti Jot. The weapons (Shastras) of the Guru are displayed daily with profound reverence.",
            highlights = listOf("UNESCO Tentative", "ASI Grade 1", "Sacred Relics (Shastras)", "Guru Ka Langar 24/7", "Laser Light & Sound Show"),
            badges = listOf("UNESCO Tentative", "Sacred Landmark", "ASI Grade 1"),
            dressCode = "Head covering & shoe cloak provided free. Modest attire covering shoulders and knees is mandatory.",
            langarInfo = "Continuous hot meals served 24/7 to all without distinction of faith or caste.",
            etiquetteList = listOf(
                EtiquetteItem("e1", "Dress Code & Head Covering", "All visitors must cover their heads with a scarf or rumal (provided free of cost at the main entrance counters). Please dress modestly with shoulders and knees covered. Footwear and socks must be deposited at the free shoe keeping stall (Joda Ghar).", "dry_cleaning"),
                EtiquetteItem("e2", "Photography Guidelines", "Photography is warmly permitted in the outer courtyards and garden parikrama. However, still cameras, video equipment, and mobile phone recording are strictly prohibited inside the inner Angitha Sahib sanctum.", "photo_camera_front"),
                EtiquetteItem("e3", "Evening Laser Light & Sound Show", "An inspiring 50-minute laser fountain and narrative presentation on the lives of the Ten Sikh Gurus takes place each evening at Gobind Bagh auditorium starting at 7:45 PM. Commentary is available in Hindi, Punjabi, and English.", "nightlife")
            ),
            nearbyPlaceIds = listOf("nanded-fort", "nagina-ghat", "kaleshwar-temple"),
            isSpotlight = true,
            isTrending = true,
            priceLevel = "Free",
            audioGuideDuration = "42 Mins · English, Hindi & Marathi",
            coordinates = "19.1517° N, 77.3182° E",
            filterTag = "Monuments"
        ),
        Place(
            id = "nanded-fort",
            name = "Nanded Fort (Nandgiri)",
            nativeName = "नांदेड किल्ला (नंदगिरी)",
            tagline = "Ancient ramparts overlooking Godavari river bend",
            categoryId = "historical",
            subCategory = "Historic Fort",
            rating = 4.6,
            reviewCount = 4800,
            distance = "3.4 km",
            distanceKm = 3.4,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCcao972HOGXDL26CCWlBwm-i_qO3Pwbsv-xN9geEQcZqonZhm3zw9bElUf5hJfejNdMjTLC-1E68cyfcJizSR2YzvsVHmjEjvT877bftbkUKX5BrELFit3dltGg9oF7_4088TAoVoE3kq5gpwE1RrQxcVIQaPNSfEpYXaBxg05yg69kX5nvC16zUXg7_HR-uFMKvUZCds1SFZ5e9LW7gr7djFuy00p-jp_mOE3KeW16tHPk7UEh1gRuA",
            address = "Killa Area, Nanded, Maharashtra",
            timings = "9:00 AM – 6:30 PM",
            scheduleDetails = "Open daily from morning sunrise to sunset. Best photography lighting 4:30 PM to 6:00 PM.",
            entryFee = "Free Entry",
            description = "Ancient stone fortress bastions perched on a hill encircled on three sides by the holy Godavari river. Offers magnificent 360-degree panoramic vantage views of Nanded town, lush riverside vegetation, and historic gateway arches.",
            historyText = "Nanded Fort was constructed strategically on a promontory over the Godavari to oversee trading boats and river crossings. It features thick stone walls and garden terraces.",
            highlights = listOf("Godavari River Panorama", "Historic Bastions", "Sunset Photography", "Garden Ruins"),
            badges = listOf("Historic", "Free Entry", "Sunset View"),
            dressCode = "Comfortable walking shoes recommended for climbing fort steps.",
            langarInfo = "Snack kiosks and fresh coconut water available outside main gates.",
            etiquetteList = listOf(
                EtiquetteItem("e4", "Safety Along Ramparts", "Exercise care near unprotected stone parapets and cliffs overlooking the river bank.", "warning"),
                EtiquetteItem("e5", "Cleanliness", "Nanded Fort is a heritage zone; littering or defacing historical stones is prohibited.", "delete")
            ),
            nearbyPlaceIds = listOf("hazur-sahib", "nagina-ghat", "godavari-promenade"),
            isSpotlight = false,
            isTrending = true,
            priceLevel = "Free",
            audioGuideDuration = "22 Mins · English & Marathi",
            coordinates = "19.1480° N, 77.3250° E",
            filterTag = "Forts & Ruins"
        ),
        Place(
            id = "kandhar-fort",
            name = "Kandhar Fort & Moat",
            nativeName = "कंधार जलदुर्ग (राष्ट्रकूट साम्राज्य)",
            tagline = "8th-century water fortress with scenic picnic spots",
            categoryId = "historical",
            subCategory = "Rashtrakuta Heritage",
            rating = 4.7,
            reviewCount = 3100,
            distance = "32 km away",
            distanceKm = 32.0,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDPggzR4aPrn4LL6VNkUGuufas_LtWuK9W9on85Fe2IItHZ14yJ2KTBTl6-CcH--8NLNRf1rXt9t4xMuoW1OrFsr52r3e-a05_14hW8wqHgvsRviG2g04eG2WHby1V-7apI0ZC2--hLvZfbNG9nn5xbDkU4OTvqsYTslRyFAcCi0Ir6kfsmI7lCVgORUDok7KkNBcQxZWUBpE5r0A2JTEZYTi3-SCiQBYWOaumHwohJRusoPETEaSckrw",
            address = "Kandhar Taluka, Nanded District",
            timings = "8:30 AM – 6:00 PM",
            scheduleDetails = "Approx 45 min drive from central Nanded. Guided tours available on weekends.",
            entryFee = "ASI Protected",
            description = "Constructed by King Krishna III of the legendary Rashtrakuta Dynasty. It is celebrated across western India for its intact defensive water moat filled with lotus plants, carved royal arches, monolithic statues, and centuries-old Persian and Devnagari inscriptions.",
            historyText = "Kandhar was once the prominent northern capital fortress of the Rashtrakuta Empire. The deep moat made it virtually impenetrable against enemy siege armies.",
            highlights = listOf("Intact Deep Moat", "Rashtrakuta Inscriptions", "Monolithic Statues", "Jal Durg Architecture"),
            badges = listOf("Heritage Gem", "ASI Protected"),
            dressCode = "Casual outdoor attire suitable for fort exploring.",
            langarInfo = "Local village restaurants nearby serve hot pithla bhakri and authentic tea.",
            etiquetteList = listOf(
                EtiquetteItem("e6", "Moat Caution", "Swimming or entering moat waters is strictly prohibited due to depth and marsh.", "water")
            ),
            nearbyPlaceIds = listOf("hazur-sahib", "hottal-temples"),
            isSpotlight = false,
            isTrending = false,
            isWeekendEscape = true,
            priceLevel = "Free",
            audioGuideDuration = "30 Mins · English & Marathi",
            coordinates = "18.9056° N, 77.1950° E",
            filterTag = "Forts & Ruins"
        ),
        Place(
            id = "kaleshwar-temple",
            name = "Kaleshwar Temple, Vishnupuri",
            nativeName = "कालेश्वर मंदिर, विष्णुपुरी",
            tagline = "Serene Hemadpanti temple complex known for riverside sunset aarti",
            categoryId = "spiritual",
            subCategory = "River Confluence",
            rating = 4.8,
            reviewCount = 2500,
            distance = "7.1 km",
            distanceKm = 7.1,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBbZYPIgyfXk87wwSlLcPfBSWfUodnAfMb3YEiUmig_jaM6DFDh6q5OxdAoabxahWIGTSbwMVk0skXnIFNsFpSDUWEHv0-q4y7AvsC4UTA3HoIf43sZEhouM82EZRkqzGYeNKbRjov_hU_88tQGUspqxi_TSsniQqLQ_rYDBnMJ4lwUli5SvTsHYwqHPKBeI-uwtAqDHm9phF4XdU4LO9hRWmGBZ9C7G_KfkOJh1Z27uG8o3esozcrfhw",
            address = "Vishnupuri Barrage Road, Nanded",
            timings = "5:30 AM – 9:00 PM",
            scheduleDetails = "Maha Aarti at 7:00 PM every evening by the river bank.",
            entryFee = "Free Entry",
            description = "An ancient Hemadpanthi stone architectural Shiva temple nestled right beside the calm waters of the Vishnupuri reservoir on the Godavari. Pilgrims flock here for blissful meditation, sacred darshan, and spectacular sunset reflections across the water.",
            historyText = "Built in the timeless Yadava-Hemadpanti stone construction method without mortar using interlocking dressed black basalt slabs.",
            highlights = listOf("Hemadpanthi Architecture", "River Confluence", "Sunset Aarti", "Vishnupuri Barrage Views"),
            badges = listOf("River Confluence", "Darshan till 9 PM"),
            dressCode = "Traditional modest attire recommended for sanctum darshan.",
            langarInfo = "Prasadam sweets and fresh coconut available at temple complex.",
            etiquetteList = listOf(
                EtiquetteItem("e7", "Footwear", "Please remove footwear outside the stone mandapa.", "checkroom")
            ),
            nearbyPlaceIds = listOf("hazur-sahib", "godavari-promenade"),
            isSpotlight = false,
            isTrending = true,
            priceLevel = "Free",
            coordinates = "19.1230° N, 77.2910° E",
            filterTag = "Hemadpanti"
        ),
        Place(
            id = "godavari-promenade",
            name = "Godavari Riverfront Promenade",
            nativeName = "गोदावरी रिव्हरफ्रंट व संगीत कारंजे",
            tagline = "Landscaped riverwalk with open amphitheater and musical fountains",
            categoryId = "parks",
            subCategory = "Evening Vibes",
            rating = 4.7,
            reviewCount = 5100,
            distance = "2.0 km",
            distanceKm = 2.0,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuAPlfRS1_Nc2C9Zfzn1zoeje3iycS-6OlnROrMIE89b2tBfzXVbjZRBV3flLnXPuP_HcHizGoCctZmTdXu7zPSTTJ_2K8TgyvboMYIpiZPoMuU4dRlDqCH7SHFuH4V87xmo9Y0Vur17_IE7oJo_-zVm7bzcqd6Fr0B6H9G6m54Ph8AzyUNyslcSw2fu23GCKwttCxjgn7gg724gOqS7yZbbEWwJvTe3sUc5jiehKLtC7ihE7qJCuQ4hfg",
            address = "Govind Bagh Ghat Road, Nanded",
            timings = "4:00 PM – 10:00 PM",
            scheduleDetails = "Dancing Musical Fountain shows daily at 7:00 PM and 8:15 PM.",
            entryFee = "Free Entry",
            description = "Beautifully developed waterfront recreation park along the sacred Godavari. Features stone paved walking tracks, manicured lawns, children play zones, pedal boating, and an evening musical fountain show.",
            historyText = "Created as part of the Nanded City Beautification and Godavari River preservation initiative.",
            highlights = listOf("Musical Dancing Fountains", "Paddle Boating", "Amphitheater", "Evening Street Food"),
            badges = listOf("Evening Vibes", "Fountain at 7 PM"),
            dressCode = "Casual wear.",
            langarInfo = "Food stalls offering chaat, kulfi, and tea along the promenade edge.",
            etiquetteList = listOf(
                EtiquetteItem("e8", "Boating Safety", "Life jackets mandatory for all paddle boat and motorboat rides.", "directions_boat")
            ),
            nearbyPlaceIds = listOf("hazur-sahib", "nagina-ghat"),
            isSpotlight = false,
            isTrending = true,
            priceLevel = "Free",
            coordinates = "19.1450° N, 77.3200° E",
            filterTag = "Riverwalk"
        ),
        Place(
            id = "nagina-ghat",
            name = "Nagina Ghat & Gurudwara",
            nativeName = "नगीना घाट व गुरुद्वारा",
            tagline = "Sacred Godavari steps where Guru Gobind Singh Ji tested merchant devotion",
            categoryId = "spiritual",
            subCategory = "Sacred Ghat",
            rating = 4.8,
            reviewCount = 3200,
            distance = "800 m",
            distanceKm = 0.8,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuBtMKVmULK8Wi1w3rp16hulRzy7rPr6ToULuwrgYjXjn3P-e61AA4XBUNAoQX_L23Rp_9yQ9K5gzM41pbfzkJogIa6DWdMthYtc8fXuQHPcVUDgPgJ41aRsRqSlfZ8M0RBrRllyyOic4wsvBXBY65GfQMVt-KrAqOxtgPVxbcPZcsi3Na_Z73KT8VRhZfS8gG-dt4ZkltQV2bbfW1tpFfRLF3RzIJZinMqWPgivBUJ6pG834Fhlc3ixdw",
            address = "Nagina Ghat Road, Central Nanded",
            timings = "Open 24 Hours",
            scheduleDetails = "Godavari Sandhya Aarti at sunset (approx 6:30 PM).",
            entryFee = "Free Entry",
            description = "One of the most sacred riverfront ghats in Nanded. Broad stone steps descend directly into the blessed waters of the Godavari. Pilgrims gather here for holy dips, evening prayer lamps, and peaceful introspection.",
            historyText = "It is associated with an episode in the life of Guru Gobind Singh Ji when a wealthy merchant from Delhi offered a precious gem (Nagina) into the river waters.",
            highlights = listOf("Holy Godavari Bathing", "Sunset Lamp Offerings", "Historic Gurudwara", "Spiritual Serenity"),
            badges = listOf("Sacred Ghat", "800 m from Hazur Sahib"),
            dressCode = "Head covering required inside the Gurudwara sanctum.",
            langarInfo = "Continuous community langar and karah prasad available.",
            etiquetteList = listOf(
                EtiquetteItem("e9", "River Bathing", "Please bathe only within marked safety railings along the ghat steps.", "pool")
            ),
            nearbyPlaceIds = listOf("hazur-sahib", "nanded-fort"),
            isSpotlight = false,
            isTrending = false,
            priceLevel = "Free",
            coordinates = "19.1490° N, 77.3190° E",
            filterTag = "River Ghats"
        ),
        Place(
            id = "hotel-city-pride",
            name = "Hotel City Pride",
            nativeName = "हॉटेल सिटी प्राईड (मराठवाडा थाळी)",
            tagline = "Special Marathwada Thali, Jowar Bhakri & Authentic Gravies",
            categoryId = "restaurants",
            subCategory = "Iconic Flavors",
            rating = 4.7,
            reviewCount = 3500,
            distance = "Station Rd",
            distanceKm = 1.8,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDUZDePOrSx63E0XJN62hdMgFipEImIGrOUs54tsuakJJAbDT9iRHAvI1SD79pEcGQu4MR_WKXTzUa2C0GCBooKsCboHTcwdhXJCooRR4lV5MScPSu41Fki51Cy7g52WsVYAtT8IfOH71YpRdyewza53Qwkmro4tJonpz8ixV-1h04sbAGhd9qZR5Gh9n1KqvtlQd_NZEgZ5DsP0oqqrt2Z7UFsTC8d0_1aqk-xl9BItp3AEgjjFmgQcw",
            address = "Station Road, Near Railway Station, Nanded",
            timings = "11:00 AM – 11:00 PM",
            scheduleDetails = "Lunch: 11:30 AM to 3:30 PM • Dinner: 7:00 PM to 11:00 PM",
            entryFee = "₹₹ (Budget Friendly)",
            description = "Famed destination for authentic Marathwada cuisine. Served in gleaming bronze plates, the signature thali features steaming hot Jowar Bhakri made over earthen griddles, spicy garlic thecha, shev bhaji, dal tadka, and seasonal curries.",
            historyText = "Serving traditional Nanded hospitality and culinary pride for over 25 years.",
            highlights = listOf("Authentic Jowar Bhakri", "Spicy Thecha", "Shev Bhaji", "Pure Ghee Preparations"),
            badges = listOf("Must Try", "Station Rd"),
            dressCode = "Casual dining.",
            langarInfo = "Family AC hall and quick-service dining available.",
            etiquetteList = emptyList(),
            nearbyPlaceIds = listOf("hazur-sahib"),
            isSpotlight = false,
            isTrending = false,
            isTasteOfMarathwada = true,
            priceLevel = "₹₹",
            coordinates = "19.1550° N, 77.3150° E",
            filterTag = "Thali Specials"
        ),
        Place(
            id = "cafe-coffee-valley",
            name = "Cafe Coffee Valley",
            nativeName = "कॅफे कॉफी व्हॅली",
            tagline = "Artisan brew bar, shakes & rooftop terrace seating",
            categoryId = "cafes",
            subCategory = "Modern Hangout",
            rating = 4.5,
            reviewCount = 1900,
            distance = "Vasant Nagar",
            distanceKm = 2.2,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDFGQd4rhJAiD7oxo6E4F9w6sVO411PQ5rfe1gGZH8Pmuskkc-gvJhNeC5diFwtrUqLP_wvsz_4VdOpcsyq1brsdPNhi7NkwNySOQvbtvJR0U0UMxBn3lLvwlk5V8d3KfsPyZbNJts7ytc9gjHbuT6NBZLTLoF_cxIK4X-T7fhn-vkls4tlAs_-tzrN7taL6SdvQuRCDdXisleeM3F1lrI5iSqBKb6ShqGtqPrOvSu5LwzfwIyyqpOrUw",
            address = "Vasant Nagar Main Road, Nanded",
            timings = "10:00 AM – 11:00 PM",
            scheduleDetails = "Rooftop acoustic evenings on Friday and Saturday.",
            entryFee = "₹ (Affordable)",
            description = "A warm, stylish youth cafe with rustic wooden furnishings, specialty pour-over coffee, chilled mint iced brew, gourmet burgers, thin crust pizzas, and decadent chocolate croissants.",
            historyText = "Started by young local entrepreneurs to bring modern third-wave coffee culture to Nanded.",
            highlights = listOf("Specialty Cold Brew", "Rooftop Seating", "High-speed Wi-Fi", "Artisan Croissants"),
            badges = listOf("Popular", "Vasant Nagar"),
            dressCode = "Casual.",
            langarInfo = "Takeaway and dine-in available.",
            etiquetteList = emptyList(),
            nearbyPlaceIds = listOf("godavari-promenade"),
            isSpotlight = false,
            isTrending = false,
            isTasteOfMarathwada = true,
            priceLevel = "₹",
            coordinates = "19.1620° N, 77.3100° E",
            filterTag = "Artisan Coffee"
        ),
        Place(
            id = "mahur-gad",
            name = "Mahur Gad Renuka Devi",
            nativeName = "माहूर गड रेणुका देवी (शक्तिपीठ)",
            tagline = "Sacred mountain shrine nestled amidst rich teak forests",
            categoryId = "weekend-getaways",
            subCategory = "Shaktipeeth Excursion",
            rating = 4.9,
            reviewCount = 8400,
            distance = "120 km away",
            distanceKm = 120.0,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuDhh3iMaVHsUHJU9CuCXmEjjjDuKxqhqdq8ddJJ8m-4wCxdsuCBYb3Wg_stmLZcUoZD0ANgepvZab8oERMxh_BmN-yx2-1XkV-ZFpOrpTk6A4mCa8BtmKrZobBcSuwBphKqmNGgnY0MjMfWp2uww4XY2VkcLUBxzxOuLLFyxW0ulqXLmCZL4Bgmck7s6_BWXCHKIvp9fDvw9dOcbYa5Oz7n-nS3-qtHA8Zq4f19EV_C0-dUhtC7Q-A4PA",
            address = "Mahur Hill Range, Nanded District",
            timings = "5:00 AM – 10:00 PM",
            scheduleDetails = "Day trip from Nanded (~2.5 hours drive via scenic Sahyadri ghat road).",
            entryFee = "Free Entry",
            description = "One of the three-and-a-half Shaktipeeths of Maharashtra, Mahur Gad is perched atop steep forested hills. It is believed to be the birthplace of Lord Dattatreya and abode of Mata Renuka Devi, enveloped in ancient legend, medicinal flora, and wild teak trees.",
            historyText = "The temple has existed since ancient Puranic times with patronage from Rashtrakuta, Yadava, and Maratha rulers.",
            highlights = listOf("Shaktipeeth Pilgrimage", "Lord Dattatreya Temple", "Scenic Ghat Road", "Teak Forest Canopy"),
            badges = listOf("Shaktipeeth Excursion", "Day pilgrimage"),
            dressCode = "Traditional pilgrim attire recommended.",
            langarInfo = "Prasad dining hall and Dharamshalas operated by temple trust.",
            etiquetteList = listOf(
                EtiquetteItem("e10", "Hill Steps", "Electric ropeway and battery vehicles available for elderly pilgrims.", "accessible")
            ),
            nearbyPlaceIds = listOf("kandhar-fort"),
            isSpotlight = false,
            isTrending = false,
            isWeekendEscape = true,
            priceLevel = "Free",
            audioGuideDuration = "35 Mins · Marathi & Hindi",
            coordinates = "19.8320° N, 77.9150° E",
            filterTag = "Hill Shrines"
        ),
        Place(
            id = "hottal-temples",
            name = "Hottal Temples (Chalukya Architecture)",
            nativeName = "होट्टल मंदिर समूह (कल्याणी चालुक्य स्थापत्य)",
            tagline = "Remarkable 11th-century temple complex renowned for stepped stone architecture",
            categoryId = "historical",
            subCategory = "Chalukya Art",
            rating = 4.8,
            reviewCount = 1200,
            distance = "75 km",
            distanceKm = 75.0,
            heroImageUrl = "https://lh3.googleusercontent.com/aida-public/AB6AXuCFw--WGO9X49lfqQhYg-GhHykYq2kTzQkGt7fk-U0Q-Iuw_u6Hd1O0699alFaLI4PYM9d252CDa4SifSuFu_SLQamlinYxQHe_uDrF2ZvWSo-XQxKW1WNzCeIzLvyOieEEmQyPOJkGa5HfQ4CiYd_TFe1ORGRNStWk3bT803Y4uOzdFPEDtt6zAhckgiALm6iNC0wK1oZnIUQACYgHXLH2N9yRKfpbNz5Ii3ZZXBDUiW4P2HkqbyDruA",
            address = "Hottal Village, Degloor Taluka, Nanded",
            timings = "7:00 AM – 6:30 PM",
            scheduleDetails = "Annual Hottal Heritage Music & Dance Festival held every winter.",
            entryFee = "Free Entry",
            description = "A treasure trove of Western Chalukya architectural mastery dating back to 1070 C.E. The temple of Siddheshwar features intricate star-shaped stepped plinths, exquisitely carved celestial dancers (Apsaras), ornate pillars, and Sanskrit stone inscriptions.",
            historyText = "Consecrated during the reign of Someshvara I and Vikramaditya VI of the Kalyani Chalukyas.",
            highlights = listOf("Star-shaped Plinth", "Lathe-turned Pillars", "Apsara Relief Sculptures", "Annual Heritage Festival"),
            badges = listOf("Chalukya Art", "Day Excursion"),
            dressCode = "Casual walking attire.",
            langarInfo = "Village refreshments and coconut stalls near entry.",
            etiquetteList = emptyList(),
            nearbyPlaceIds = listOf("kandhar-fort"),
            isSpotlight = false,
            isTrending = false,
            isWeekendEscape = true,
            priceLevel = "Free",
            coordinates = "18.5910° N, 77.6210° E",
            filterTag = "Monuments"
        )
    )

    val triviaList: List<Trivia> = listOf(
        Trivia("t1", "Did you know?", "Nanded was known as 'Nanditat' under the Satavahana dynasty, thriving as a premier Vedic center on the Godavari banks.", "Satavahana Dynasty"),
        Trivia("t2", "Godavari's Sacred Curve", "The Godavari river flows eastward, but in Nanded it uniquely curves northwards (Uttaravahini), making bath offerings extraordinarily auspicious.", "Puranic Geography"),
        Trivia("t3", "Royal Patronage", "Maharaja Ranjit Singh sent his finest artisan sculptors from Lahore to Nanded to adorn the Angitha Sahib sanctum with pure beaten gold leaf and lapis lazuli in 1832.", "Sikh Empire History")
    )

    private fun loadSavedFavorites(): Set<String> {
        val saved = prefs?.getStringSet("key_favorites", null)
        return saved ?: setOf("hazur-sahib", "nanded-fort")
    }

    private fun loadRecentlyViewed(): List<String> {
        val raw = prefs?.getString("key_recently_viewed", null)
        return if (!raw.isNullOrBlank()) {
            raw.split(",").filter { it.isNotBlank() }
        } else {
            listOf("hazur-sahib", "nanded-fort", "kandhar-fort")
        }
    }

    fun toggleFavorite(placeId: String) {
        val current = _favorites.value.toMutableSet()
        if (current.contains(placeId)) {
            current.remove(placeId)
        } else {
            current.add(placeId)
        }
        _favorites.value = current
        prefs?.edit()?.putStringSet("key_favorites", current)?.apply()
    }

    fun isFavorite(placeId: String): Boolean {
        return _favorites.value.contains(placeId)
    }

    fun markViewed(placeId: String) {
        val current = _recentlyViewed.value.toMutableList()
        current.remove(placeId)
        current.add(0, placeId)
        val trimmed = current.take(10)
        _recentlyViewed.value = trimmed
        prefs?.edit()?.putString("key_recently_viewed", trimmed.joinToString(","))?.apply()
    }

    fun getPlaceById(id: String): Place? {
        return places.find { it.id == id }
    }

    fun getPlacesByCategory(categoryId: String): List<Place> {
        return places.filter { it.categoryId == categoryId }
    }

    fun searchPlaces(query: String): List<Place> {
        if (query.isBlank()) return places
        val q = query.trim().lowercase()
        return places.filter {
            it.name.lowercase().contains(q) ||
            it.nativeName.lowercase().contains(q) ||
            it.description.lowercase().contains(q) ||
            it.subCategory.lowercase().contains(q) ||
            it.address.lowercase().contains(q) ||
            it.categoryId.lowercase().contains(q)
        }
    }
}
