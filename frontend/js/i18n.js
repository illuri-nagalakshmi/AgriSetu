/**
 * AgriSetu Multilingual Internationalization (i18n) Module
 * Supports: English ('en'), Telugu ('te'), Hindi ('hi')
 * Persists selection in localStorage and synchronizes with SQL Server user profile when authenticated.
 */

const translations = {
  en: {
    // Brand
    brand_name: "AgriSetu",
    brand_tagline: "Connecting Farmers • Empowering Labourers",
    
    // Navigation
    nav_home: "Home",
    nav_find_work: "Find Work",
    nav_hire_workers: "Hire Workers",
    nav_about: "About",
    nav_privacy: "Privacy",
    nav_login: "Login",
    nav_register: "Register",
    nav_dashboard: "Dashboard",
    nav_manage_jobs: "Manage Jobs",
    nav_post_job: "Post Job",
    nav_my_applications: "My Applications",
    nav_assignments: "Work Assignments",
    nav_saved_jobs: "Saved Jobs",
    nav_notifications: "Notifications",
    nav_profile: "My Profile",
    nav_logout: "Logout",
    nav_admin: "Admin Console",

    // Hero Section
    hero_title: "Connecting Farmers with Skilled Agricultural Labourers",
    hero_subtitle: "A trusted community platform bridging local agricultural work opportunities with transparent daily wages, fair terms, and mutual respect.",
    btn_find_work: "Find Agricultural Work",
    btn_hire_workers: "Post a Job / Hire Workers",

    // Search and Filters
    search_category_placeholder: "All Categories",
    search_district_placeholder: "Search District or Village...",
    search_min_wage: "Min Wage (₹)",
    search_max_wage: "Max Wage (₹)",
    btn_search: "Search Jobs",
    btn_reset: "Reset Filters",

    // Section Titles
    how_it_works_title: "How AgriSetu Works",
    how_farmer_title: "For Farmers",
    how_farmer_step1: "Post job details, required number of workers, and transparent wages.",
    how_farmer_step2: "Review verified local labourer applications with skills & experience.",
    how_farmer_step3: "Accept workers with real-time vacancy tracking and zero overbooking.",
    how_farmer_step4: "Confirm completed work, reveal contact numbers, and share mutual ratings.",

    how_labourer_title: "For Labourers",
    how_labourer_step1: "Search verified local jobs by village, crop category, and wage.",
    how_labourer_step2: "Apply with 1-click without revealing your phone number publicly.",
    how_labourer_step3: "Receive instant notifications when your application is accepted.",
    how_labourer_step4: "Access direct farm contact, complete work, and build your digital reputation.",

    features_title: "Why AgriSetu?",
    feat_local_title: "Local Employment",
    feat_local_desc: "Discover work opportunities directly within your village and district without middleman commission.",
    feat_wage_title: "Transparent Wages",
    feat_wage_desc: "Clear daily or hourly wage rates, meal provisions, and working hours visible upfront.",
    feat_privacy_title: "Contact Privacy",
    feat_privacy_desc: "Phone numbers remain protected and are disclosed only after an application is confirmed.",
    feat_allocation_title: "Smart Worker Allocation",
    feat_allocation_desc: "Strict vacancy accounting prevents overbooking and guarantees fair allocation.",

    featured_jobs_title: "Recent Agricultural Job Openings",
    view_all_jobs: "View All Jobs →",

    // Job Card Labels
    label_wage: "Wage",
    label_per_day: "/ day",
    label_per_hour: "/ hr",
    label_location: "Location",
    label_dates: "Dates",
    label_vacancies: "Vacancies",
    label_required: "Required",
    label_confirmed: "Confirmed",
    label_remaining: "Remaining",
    btn_view_details: "View Details",
    btn_apply_now: "Apply Now",
    btn_applied: "Applied",
    btn_save: "Save",
    btn_saved: "Saved",

    // Dashboard Farmer
    dash_farmer_title: "Farmer Dashboard",
    dash_total_jobs: "Total Jobs Posted",
    dash_active_jobs: "Active Open Jobs",
    dash_applications_received: "Applications Received",
    dash_confirmed_workers: "Confirmed Workers",
    dash_remaining_vacancies: "Total Remaining Vacancies",

    // Dashboard Labourer
    dash_labourer_title: "Labourer Dashboard",
    dash_total_apps: "Total Applications",
    dash_pending_apps: "Pending Decision",
    dash_accepted_apps: "Accepted Applications",
    dash_active_assignments: "Active Assignments",
    dash_completed_assignments: "Completed Assignments",

    // Statuses
    status_published: "Active / Published",
    status_draft: "Draft",
    status_paused: "Paused",
    status_closed: "Closed",
    status_cancelled: "Cancelled",
    status_completed: "Completed",
    status_pending: "Pending",
    status_accepted: "Accepted",
    status_rejected: "Rejected",
    status_withdrawn: "Withdrawn",
    status_confirmed: "Confirmed",

    // Auth
    login_title: "Login to AgriSetu",
    register_title: "Create an AgriSetu Account",
    label_full_name: "Full Name",
    label_email: "Email Address",
    label_phone: "Mobile Number",
    label_password: "Password",
    label_confirm_password: "Confirm Password",
    label_role: "I want to register as:",
    role_farmer: "Farmer (Looking to hire agricultural workers)",
    role_labourer: "Agricultural Labourer (Looking for work)",
    label_preferred_language: "Preferred Language",
    label_village: "Village / Town",
    label_district: "District",
    label_state: "State",
    label_privacy_consent: "I agree to the privacy policy and community terms",
    btn_login: "Login",
    btn_register: "Register Account",
    no_account: "Don't have an account? Register here",
    have_account: "Already registered? Login here",

    // Footer
    footer_desc: "AgriSetu is a Community Service Project (CSP) empowering rural livelihoods by directly connecting farmers with skilled agricultural labourers across India.",
    footer_quick_links: "Quick Links",
    footer_support: "Support & Safety",
    footer_csp_notice: "CSP Project Demonstration • Secure & Non-Profit Initiative",
    footer_rights: "All rights reserved."
  },

  te: {
    // Brand
    brand_name: "అగ్రిసేతు",
    brand_tagline: "రైతులను కలుపుతూ • శ్రామికులకు సాధికారత కల్పిస్తూ",

    // Navigation
    nav_home: "హోమ్",
    nav_find_work: "పని వెతకండి",
    nav_hire_workers: "కూలీలను నియమించండి",
    nav_about: "మా గురించి",
    nav_privacy: "గోప్యత",
    nav_login: "లాగిన్",
    nav_register: "రిజిస్టర్",
    nav_dashboard: "డ్యాష్‌బోర్డ్",
    nav_manage_jobs: "పనుల నిర్వహణ",
    nav_post_job: "పని పోస్ట్ చేయండి",
    nav_my_applications: "నా దరఖాస్తులు",
    nav_assignments: "పని కేటాయింపులు",
    nav_saved_jobs: "దాచిన పనులు",
    nav_notifications: "నోటిఫికేషన్లు",
    nav_profile: "నా ప్రొఫైల్",
    nav_logout: "లాగ్ అవుట్",
    nav_admin: "అడ్మిన్ ప్యానెల్",

    // Hero Section
    hero_title: "రైతులను నైపుణ్యం కలిగిన వ్యవసాయ శ్రామికులతో అనుసంధానించడం",
    hero_subtitle: "స్థానిక వ్యవసాయ పనులను పారదర్శకమైన రోజువారీ కూలీతో, దళారుల ప్రమేయం లేకుండా అందించే విశ్వసనీయ వేదిక.",
    btn_find_work: "వ్యవసాయ పనులను వెతకండి",
    btn_hire_workers: "పని పోస్ట్ చేయండి / కూలీలను నియమించండి",

    // Search and Filters
    search_category_placeholder: "అన్ని రకాల పనులు",
    search_district_placeholder: "జిల్లా లేదా గ్రామం వెతకండి...",
    search_min_wage: "కనిష్ట కూలీ (₹)",
    search_max_wage: "గరిష్ట కూలీ (₹)",
    btn_search: "పనులను వెతకండి",
    btn_reset: "రీసెట్ చేయండి",

    // Section Titles
    how_it_works_title: "అగ్రిసేతు ఎలా పనిచేస్తుంది?",
    how_farmer_title: "రైతుల కోసం",
    how_farmer_step1: "పని వివరాలు, కావలసిన కూలీల సంఖ్య మరియు రోజువారీ కూలీని నమోదు చేయండి.",
    how_farmer_step2: "స్థానిక శ్రామికుల నైపుణ్యాలు మరియు అనుభవాన్ని పరిశీలించండి.",
    how_farmer_step3: "పరిమితికి మించకుండా సరైన కూలీలను ఆమోదించండి.",
    how_farmer_step4: "పని పూర్తయిన తర్వాత పరస్పర రేటింగ్ మరియు సమీక్ష ఇవ్వండి.",

    how_labourer_title: "శ్రామికుల కోసం",
    how_labourer_step1: "మీ గ్రామం మరియు జిల్లా పరిధిలోని వ్యవసాయ పనులను సులభంగా చూడండి.",
    how_labourer_step2: "మీ ఫోన్ నంబర్ గోప్యంగా ఉంచుతూ ఒకే క్లిక్‌తో దరఖాస్తు చేసుకోండి.",
    how_labourer_step3: "రైతు మీ దరఖాస్తును ఆమోదించిన వెంటనే నోటిఫికేషన్ పొందండి.",
    how_labourer_step4: "పని పూర్తి చేసి మంచి రేటింగ్ పొందడం ద్వారా ఎక్కువ పనులను పొందండి.",

    features_title: "అగ్రిసేతు ఎందుకు?",
    feat_local_title: "స్థానిక ఉపాధి",
    feat_local_desc: "మీ స్వంత గ్రామం మరియు పరిసర ప్రాంతాలలో ఎలాంటి కమీషన్ లేకుండా పని పొందండి.",
    feat_wage_title: "పారదర్శక కూలీ",
    feat_wage_desc: "రోజువారీ లేదా గంటల కూలీ, భోజన సౌకర్యాల వివరాలు ముందుగానే తెలుసుకోండి.",
    feat_privacy_title: "సంప్రదింపుల గోప్యత",
    feat_privacy_desc: "మీ దరఖాస్తు ఆమోదించబడే వరకు మీ మొబైల్ నంబర్ ఇతరులకు కనిపించదు.",
    feat_allocation_title: "ఖచ్చితమైన కూలీల కేటాయింపు",
    feat_allocation_desc: "అవసరమైన కూలీల సంఖ్య మించకుండా సరైన సాంకేతిక నియంత్రణ ఉంటుంది.",

    featured_jobs_title: "ఇటీవలి వ్యవసాయ పనులు",
    view_all_jobs: "అన్ని పనులను చూడండి →",

    // Job Card Labels
    label_wage: "కూలీ",
    label_per_day: "/ రోజుకు",
    label_per_hour: "/ గంటకు",
    label_location: "ప్రాంతం",
    label_dates: "తేదీలు",
    label_vacancies: "ఖాళీలు",
    label_required: "కావలసిన వారు",
    label_confirmed: "ఖరారైన వారు",
    label_remaining: "మిగిలిన ఖాళీలు",
    btn_view_details: "వివరాలు చూడండి",
    btn_apply_now: "దరఖాస్తు చేసుకోండి",
    btn_applied: "దరఖాస్తు చేశారు",
    btn_save: "సేవ్ చేయండి",
    btn_saved: "సేవ్ చేయబడింది",

    // Dashboard Farmer
    dash_farmer_title: "రైతు డ్యాష్‌బోర్డ్",
    dash_total_jobs: "మొత్తం పోస్ట్ చేసిన పనులు",
    dash_active_jobs: "ప్రస్తుతం నడుస్తున్న పనులు",
    dash_applications_received: "వచ్చిన దరఖాస్తులు",
    dash_confirmed_workers: "ఖరారైన కూలీలు",
    dash_remaining_vacancies: "మిగిలిన మొత్తం ఖాళీలు",

    // Dashboard Labourer
    dash_labourer_title: "శ్రామికుల డ్యాష్‌బోర్డ్",
    dash_total_apps: "మొత్తం దరఖాస్తులు",
    dash_pending_apps: "పరిశీలనలో ఉన్నవి",
    dash_accepted_apps: "ఆమోదించబడినవి",
    dash_active_assignments: "ప్రస్తుత పనులు",
    dash_completed_assignments: "పూర్తయిన పనులు",

    // Statuses
    status_published: "ప్రచురించబడింది",
    status_draft: "చిత్తుప్రతి",
    status_paused: "నిలిపివేయబడింది",
    status_closed: "ముగిసింది",
    status_cancelled: "రద్దు చేయబడింది",
    status_completed: "పూర్తయింది",
    status_pending: "పరిశీలనలో ఉంది",
    status_accepted: "ఆమోదించబడింది",
    status_rejected: "తిరస్కరించబడింది",
    status_withdrawn: "ఉపసంహరించబడింది",
    status_confirmed: "ఖరారైంది",

    // Auth
    login_title: "అగ్రిసేతు లాగిన్",
    register_title: "అగ్రిసేతు ఖాతా తెరవండి",
    label_full_name: "పూర్తి పేరు",
    label_email: "ఇమెయిల్ చిరునామా",
    label_phone: "మొబైల్ నంబర్",
    label_password: "పాస్‌వర్డ్",
    label_confirm_password: "పాస్‌వర్డ్ ధృవీకరించండి",
    label_role: "రిజిస్ట్రేషన్ రకం:",
    role_farmer: "రైతు (కూలీల కోసం చూస్తున్నాను)",
    role_labourer: "వ్యవసాయ శ్రామికుడు (పని కోసం చూస్తున్నాను)",
    label_preferred_language: "ఇష్టపడే భాష",
    label_village: "గ్రామం / ఊరు",
    label_district: "జిల్లా",
    label_state: "రాష్ట్రం",
    label_privacy_consent: "నేను గోప్యతా విధానం మరియు నిబంధనలను అంగీకరిస్తున్నాను",
    btn_login: "లాగిన్ చేయండి",
    btn_register: "ఖాతా సృష్టించండి",
    no_account: "ఖాతా లేదా? ఇక్కడ నమోదు చేసుకోండి",
    have_account: "ఇప్పటికే ఖాతా ఉందా? లాగిన్ అవ్వండి",

    // Footer
    footer_desc: "అగ్రిసేతు అనేది గ్రామీణ జీవనోపాధిని మెరుగుపరిచే కమ్యూనిటీ సేవా ప్రాజెక్ట్ (CSP).",
    footer_quick_links: "ముఖ్యమైన లింకులు",
    footer_support: "సహాయం & రక్షణ",
    footer_csp_notice: "CSP ప్రాజెక్ట్ డెమో • లాభాపేక్ష లేని సామాజిక చొరవ",
    footer_rights: "సర్వ హక్కులు ప్రత్యేకించబడ్డాయి."
  },

  hi: {
    // Brand
    brand_name: "एग्रीसेतु",
    brand_tagline: "किसानों को जोड़ना • मजदूरों को सशक्त बनाना",

    // Navigation
    nav_home: "होम",
    nav_find_work: "काम खोजें",
    nav_hire_workers: "मजदूर रखें",
    nav_about: "हमारे बारे में",
    nav_privacy: "गोपनीयता",
    nav_login: "लॉग इन",
    nav_register: "पंजीकरण",
    nav_dashboard: "डैशबोर्ड",
    nav_manage_jobs: "कार्य प्रबंधन",
    nav_post_job: "काम पोस्ट करें",
    nav_my_applications: "मेरे आवेदन",
    nav_assignments: "कार्य आवंटन",
    nav_saved_jobs: "सहेजे गए काम",
    nav_notifications: "सूचनाएं",
    nav_profile: "मेरी प्रोफ़ाइल",
    nav_logout: "लॉग आउट",
    nav_admin: "एडमिन पैनल",

    // Hero Section
    hero_title: "किसानों को कुशल कृषि मजदूरों से जोड़ना",
    hero_subtitle: "पारदर्शी दैनिक मजदूरी, बिना किसी बिचौलिए के स्थानीय कृषि रोजगार प्रदान करने वाला विश्वसनीय मंच।",
    btn_find_work: "कृषि कार्य खोजें",
    btn_hire_workers: "कार्य पोस्ट करें / मजदूर खोजें",

    // Search and Filters
    search_category_placeholder: "सभी श्रेणियां",
    search_district_placeholder: "जिला या गांव खोजें...",
    search_min_wage: "न्यूनतम मजदूरी (₹)",
    search_max_wage: "अधिकतम मजदूरी (₹)",
    btn_search: "काम खोजें",
    btn_reset: "रीसेट करें",

    // Section Titles
    how_it_works_title: "एग्रीसेतु कैसे काम करता है?",
    how_farmer_title: "किसानों के लिए",
    how_farmer_step1: "काम का विवरण, आवश्यक मजदूरों की संख्या और मजदूरी दर्ज करें।",
    how_farmer_step2: "स्थानीय श्रमिकों के कौशल और अनुभव की समीक्षा करें।",
    how_farmer_step3: "मजदूरों को स्वीकार करें, क्षमता से अधिक बुकिंग नहीं होगी।",
    how_farmer_step4: "काम पूरा होने के बाद संपर्क साझा करें और रेटिंग दें।",

    how_labourer_title: "मजदूरों के लिए",
    how_labourer_step1: "अपने गांव और जिले में कृषि कार्यों की खोज करें।",
    how_labourer_step2: "अपना फोन नंबर गुप्त रखते हुए 1-क्लिक में आवेदन करें।",
    how_labourer_step3: "आवेदन स्वीकार होने पर तुरंत सूचना प्राप्त करें।",
    how_labourer_step4: "सीधे किसान से संपर्क करें, काम पूरा करें और अच्छी रेटिंग पाएं।",

    features_title: "एग्रीसेतु क्यों?",
    feat_local_title: "स्थानीय रोजगार",
    feat_local_desc: "बिना किसी दलाली के अपने गांव और जिले में सीधे रोजगार पाएं।",
    feat_wage_title: "पारदर्शी मजदूरी",
    feat_wage_desc: "दैनिक मजदूरी, भोजन व्यवस्था और काम के घंटे पहले से स्पष्ट देखें।",
    feat_privacy_title: "संपर्क गोपनीयता",
    feat_privacy_desc: "आवेदन स्वीकृत होने से पहले फोन नंबर सुरक्षित रहता है।",
    feat_allocation_title: "सटीक मजदूर आवंटन",
    feat_allocation_desc: "आवश्यक मजदूरों से अधिक की बुकिंग रोकने की सुरक्षित प्रणाली।",

    featured_jobs_title: "हाल ही में पोस्ट किए गए कृषि कार्य",
    view_all_jobs: "सभी काम देखें →",

    // Job Card Labels
    label_wage: "मजदूरी",
    label_per_day: "/ दिन",
    label_per_hour: "/ घंटा",
    label_location: "स्थान",
    label_dates: "तिथियां",
    label_vacancies: "रिक्तियां",
    label_required: "आवश्यक",
    label_confirmed: "स्वीकृत",
    label_remaining: "शेष रिक्तियां",
    btn_view_details: "विवरण देखें",
    btn_apply_now: "आवेदन करें",
    btn_applied: "आवेदन किया गया",
    btn_save: "सहेजें",
    btn_saved: "सहेजा गया",

    // Dashboard Farmer
    dash_farmer_title: "किसान डैशबोर्ड",
    dash_total_jobs: "कुल पोस्ट किए गए काम",
    dash_active_jobs: "सक्रिय काम",
    dash_applications_received: "प्राप्त आवेदन",
    dash_confirmed_workers: "स्वीकृत मजदूर",
    dash_remaining_vacancies: "कुल शेष रिक्तियां",

    // Dashboard Labourer
    dash_labourer_title: "मजदूर डैशबोर्ड",
    dash_total_apps: "कुल आवेदन",
    dash_pending_apps: "लंबित आवेदन",
    dash_accepted_apps: "स्वीकृत आवेदन",
    dash_active_assignments: "वर्तमान कार्य",
    dash_completed_assignments: "पूर्ण कार्य",

    // Statuses
    status_published: "सक्रिय / प्रकाशित",
    status_draft: "ड्राफ्ट",
    status_paused: "रोका गया",
    status_closed: "समाप्त",
    status_cancelled: "रद्द",
    status_completed: "पूर्ण",
    status_pending: "लंबित",
    status_accepted: "स्वीकृत",
    status_rejected: "अस्वीकृत",
    status_withdrawn: "वापस लिया गया",
    status_confirmed: "पुष्ट",

    // Auth
    login_title: "एग्रीसेतु में लॉग इन करें",
    register_title: "एग्रीसेतु खाता बनाएं",
    label_full_name: "पूरा नाम",
    label_email: "ईमेल पता",
    label_phone: "मोबाइल नंबर",
    label_password: "पासवर्ड",
    label_confirm_password: "पासवर्ड की पुष्टि करें",
    label_role: "पंजीकरण प्रकार:",
    role_farmer: "किसान (मजदूरों की आवश्यकता है)",
    role_labourer: "कृषि मजदूर (काम की तलाश है)",
    label_preferred_language: "पसंदीदा भाषा",
    label_village: "गांव / शहर",
    label_district: "जिला",
    label_state: "राज्य",
    label_privacy_consent: "मैं गोपनीयता नीति और समुदाय की शर्तों से सहमत हूं",
    btn_login: "लॉग इन करें",
    btn_register: "खाता बनाएं",
    no_account: "खाता नहीं है? यहां पंजीकरण करें",
    have_account: "पहले से खाता है? लॉग इन करें",

    // Footer
    footer_desc: "एग्रीसेतु एक समुदाय सेवा परियोजना (CSP) है जो किसानों और कृषि श्रमिकों को सीधे जोड़ती है।",
    footer_quick_links: "त्वरित लिंक",
    footer_support: "सहायता एवं सुरक्षा",
    footer_csp_notice: "CSP परियोजना प्रदर्शन • गैर-लाभकारी सामाजिक पहल",
    footer_rights: "सर्वाधिकार सुरक्षित।"
  }
};

let currentLanguage = localStorage.getItem('agrisetu_lang') || 'en';

function setLanguage(lang) {
  if (!translations[lang]) lang = 'en';
  currentLanguage = lang;
  localStorage.setItem('agrisetu_lang', lang);

  // Update language select dropdowns
  document.querySelectorAll('.lang-selector').forEach(sel => {
    sel.value = lang;
  });

  // Apply translations to elements with data-i18n attribute
  document.querySelectorAll('[data-i18n]').forEach(el => {
    const key = el.getAttribute('data-i18n');
    if (translations[lang] && translations[lang][key]) {
      el.textContent = translations[lang][key];
    }
  });

  // Apply placeholder translations
  document.querySelectorAll('[data-i18n-placeholder]').forEach(el => {
    const key = el.getAttribute('data-i18n-placeholder');
    if (translations[lang] && translations[lang][key]) {
      el.setAttribute('placeholder', translations[lang][key]);
    }
  });

  // If user is authenticated, sync preferred language with SQL Server backend
  if (window.api && window.auth && window.auth.isLoggedIn()) {
    window.api.put('/api/users/language', { language: lang }).catch(() => {});
  }
}

function t(key, fallback = '') {
  if (translations[currentLanguage] && translations[currentLanguage][key]) {
    return translations[currentLanguage][key];
  }
  if (translations.en[key]) {
    return translations.en[key];
  }
  return fallback || key;
}

// Auto-initialize on DOM ready
document.addEventListener('DOMContentLoaded', () => {
  setLanguage(currentLanguage);

  document.querySelectorAll('.lang-selector').forEach(sel => {
    sel.addEventListener('change', (e) => {
      setLanguage(e.target.value);
    });
  });
});

window.i18n = {
  setLanguage,
  t,
  getCurrentLanguage: () => currentLanguage
};
