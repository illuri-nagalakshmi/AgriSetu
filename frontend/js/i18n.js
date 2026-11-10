/**
 * AgriSetu Multilingual Internationalization (i18n) Module
 * Supports: English ('en'), Telugu ('te'), Hindi ('hi')
 * Persists selection in localStorage and synchronizes across all pages and views.
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
    hero_badge: "🌾 Digital Agricultural Employment Network",
    hero_title: "Connecting Farmers with Skilled Agricultural Labourers",
    hero_subtitle: "A trusted platform bridging local agricultural work opportunities with transparent daily wages, fair terms, and mutual respect.",
    btn_find_work: "Find Agricultural Work",
    btn_hire_workers: "Hire Workers / Post Job",
    hero_search_title: "🔍 Find Work Near You",

    // Crop Work Categories
    cat_all: "All Categories",
    cat_harvesting: "Harvesting",
    cat_sowing: "Sowing",
    cat_ploughing: "Ploughing",
    cat_weeding: "Weeding",
    cat_irrigation: "Irrigation",
    cat_planting: "Planting",
    cat_fertilizer_application: "Fertilizer Application",
    cat_fruit_picking: "Fruit Picking",
    cat_general_agricultural_labour: "General Agricultural Labour",

    // Filters and Search
    filter_category: "Category",
    filter_location: "District / Location",
    filter_location_placeholder: "e.g. Guntur, Nizamabad, Tenali...",
    filter_min_wage: "Min Daily Wage (₹)",
    filter_max_wage: "Max Daily Wage (₹)",
    filter_min_wage_placeholder: "Min ₹",
    filter_sort: "Sort By",
    sort_newest: "Newest First",
    sort_wage_desc: "Wage: High to Low",
    sort_wage_asc: "Wage: Low to High",
    sort_date: "Earliest Start Date",
    filter_meals: "Meals Provided",
    filter_accommodation: "Accommodation",
    filter_any: "Any",
    filter_yes_meals: "Yes, Meals Provided",
    btn_search: "Search Jobs",
    btn_apply_filters: "Apply Filters",
    btn_reset: "Reset",
    btn_clear_filters: "Clear All Filters",

    // Section Titles
    categories_section_title: "Agricultural Work Categories",
    categories_section_subtitle: "Specialized farming operations connecting experienced workers with local field requirements.",
    how_it_works_title: "How AgriSetu Works",
    how_it_works_subtitle: "A transparent, structured 4-step workflow connecting rural supply and demand.",
    how_farmer_title: "For Farmers",
    how_farmer_step1: "Post job details, required number of workers, and transparent daily wages.",
    how_farmer_step2: "Review verified local labourer applications with skills & experience.",
    how_farmer_step3: "Accept workers with real-time vacancy tracking and zero overbooking.",
    how_farmer_step4: "Confirm completed work, reveal contact numbers, and share mutual ratings.",

    how_labourer_title: "For Agricultural Labourers",
    how_labourer_step1: "Search verified local jobs by village, crop category, and wage.",
    how_labourer_step2: "Apply with 1-click without revealing your phone number publicly.",
    how_labourer_step3: "Receive instant notifications when your application is accepted.",
    how_labourer_step4: "Access direct farm contact, complete work, and build your digital reputation.",

    features_title: "Why Choose AgriSetu?",
    features_subtitle: "Engineered specifically for agricultural communities with privacy, transparency, and multi-language access.",
    feat_local_title: "Local Employment",
    feat_local_desc: "Discover work opportunities directly within your village and district without middleman commission.",
    feat_wage_title: "Transparent Wages",
    feat_wage_desc: "Clear daily or hourly wage rates, meal provisions, and working hours visible upfront.",
    feat_privacy_title: "Contact Privacy",
    feat_privacy_desc: "Phone numbers remain protected and are disclosed only after an application is confirmed.",
    feat_allocation_title: "Smart Worker Allocation",
    feat_allocation_desc: "Strict vacancy accounting prevents overbooking and guarantees fair allocation.",

    featured_jobs_title: "Recent Agricultural Job Openings",
    featured_jobs_subtitle: "Browse live agricultural vacancies posted directly by verified local farmers.",
    view_all_jobs: "View All Jobs →",

    // Mission Section
    mission_title: "Our Agricultural Employment Mission",
    mission_text: "AgriSetu connects farmers facing seasonal labour shortages directly with skilled agricultural workers looking for reliable local employment. By eliminating exploitative intermediaries, offering Telugu, Hindi, and English access, and enforcing strict contact privacy, AgriSetu empowers rural livelihoods through transparent technology.",
    btn_read_mission: "Learn More About AgriSetu",
    btn_safety_standards: "Safety & Privacy Standards",

    // Job Card Labels
    jobs_page_title: "Find Agricultural Work",
    jobs_page_subtitle: "Browse live agricultural vacancies posted directly by verified farmers across districts.",
    label_wage: "Wage",
    card_per_day: "/ day",
    card_per_hr: "/ hr",
    label_location: "Location",
    label_dates: "Dates",
    label_vacancies: "Vacancies",
    label_capacity: "Capacity:",
    label_workers: "Workers",
    card_full: "FULL (0 vacancies)",
    card_vacancies_open: "vacancies open",
    card_meals: "Meals Provided",
    card_transport: "Transport Arranged",
    card_accommodation: "Accommodation Provided",
    btn_view_details: "View Details",
    btn_apply_now: "Apply Now",
    btn_applied: "Applied",
    btn_save: "☆ Save",
    btn_saved: "★ Saved",
    btn_fully_staffed: "Fully Staffed",

    // Loading & Empty States
    jobs_loading: "Loading agricultural jobs from database...",
    jobs_matching_loading: "Loading matching jobs...",
    jobs_empty_title: "No matching agricultural jobs found",
    jobs_empty_desc: "Try adjusting your location, wage filter, or work category.",
    jobs_error_title: "Failed to load jobs",
    jobs_error_desc: "Unable to retrieve job postings right now. Please try again.",
    btn_retry: "Retry",
    no_jobs_posted_yet: "No agricultural jobs posted yet in the database.",
    btn_post_first_job: "Post the First Job as Farmer",

    // Apply Modal
    modal_apply_title: "Apply for Agricultural Work",
    privacy_notice_title: "Contact Privacy:",
    privacy_notice_text: "Your mobile number will remain hidden from the farmer until they review and accept your application.",
    label_cover_note: "Cover Note / Message to Farmer (Optional)",
    placeholder_cover_note: "Describe your experience with this crop, tools you can bring, or team availability...",
    btn_cancel: "Cancel",
    btn_submit_app: "Submit Application",
    btn_submitting: "Submitting...",
    app_submitted_success: "Application submitted successfully! The farmer has been notified.",
    app_submitted_error: "Failed to submit application. Please try again.",

    // Login Page
    login_title: "Login to AgriSetu",
    login_subtitle: "Connecting Farmers • Empowering Labourers",
    login_banner_quote: "Empowering rural communities through direct agricultural connections and transparent wages.",
    login_role_guide_title: "Select Role Guidance",
    role_farmer_name: "Farmer",
    role_labourer_name: "Labourer",
    role_admin_name: "Admin",
    role_farmer_desc: "Farmers can post agricultural job openings, specify daily wages, review worker applications, and manage field crews.",
    role_labourer_desc: "Agricultural labourers can discover nearby crop work, apply directly with contact privacy, and track assignments.",
    role_admin_desc: "Administrators monitor platform operations, manage users, review agricultural jobs, and resolve inquiries.",
    role_login_tip: "Tip for evaluation: Enter your registered credentials or use standard demo accounts to test.",
    label_email: "Email Address",
    label_password: "Password",
    placeholder_email: "name@example.com",
    placeholder_password: "••••••••",
    btn_logging_in: "Logging in...",
    no_account: "Don't have an account? Register here",
    have_account: "Already registered? Login here",

    // Registration Page
    register_title: "Create an AgriSetu Account",
    label_full_name: "Full Name",
    placeholder_full_name: "e.g. Ramesh Kumar",
    label_phone: "Mobile Number",
    placeholder_phone: "+91 98480 12345",
    label_confirm_password: "Confirm Password",
    label_role: "I want to register as:",
    role_farmer_radio: "Farmer (Looking to hire agricultural workers)",
    role_labourer_radio: "Agricultural Labourer (Looking for work)",
    label_preferred_language: "Preferred Language",
    label_village: "Village / Town",
    placeholder_village: "e.g. Tenali",
    label_district: "District",
    placeholder_district: "e.g. Guntur",
    label_state: "State",
    placeholder_state: "e.g. Andhra Pradesh",
    label_privacy_consent: "I agree to the privacy policy and community terms",
    btn_register: "Register Account",
    btn_registering: "Registering...",

    // About Page (Professional, No CSP wording)
    about_badge: "Agricultural Employment Platform",
    about_hero_title: "About AgriSetu",
    about_hero_subtitle: "Connecting Farmers. Empowering Labourers.",
    about_intro_title: "1. Background & Agricultural Mission",
    about_intro_p1: "Agriculture is the lifeblood of rural India, yet farmers frequently experience critical delays during harvesting and sowing seasons due to acute local labour shortages. Simultaneously, agricultural workers and daily-wage labourers face underemployment because they lack direct visibility into which nearby farms have open vacancies.",
    about_intro_p2: "Traditionally, this information gap has been exploited by unregulated labour contractors who take a large cut of the worker's earnings while providing zero transparency or safety assurances.",
    about_intro_p3: "AgriSetu (The Agricultural Bridge) provides a direct, transparent, and mobile-accessible digital link between farmers and agricultural workers.",
    about_prin_title: "2. Core Platform Engineering Principles",
    about_prin_1_title: "Concurrency-Safe Worker Allocation:",
    about_prin_1_text: "Mathematical capacity guarantee (Remaining Vacancies = Required Workers - Confirmed Workers) enforced with database row-level locking to eliminate overbooking and ghost assignments.",
    about_prin_2_title: "Contact Privacy by Default:",
    about_prin_2_text: "Labourers' and farmers' mobile numbers are protected against spam; mutual contact details are only disclosed when a formal work assignment is confirmed.",
    about_prin_3_title: "Multilingual Access:",
    about_prin_3_text: "Complete support for Telugu, Hindi, and English ensures rural agricultural workers can navigate the platform comfortably in their native language.",
    about_prin_4_title: "Transparent Terms:",
    about_prin_4_text: "Clear daily wages, meal arrangements, transport options, and working hours are fully disclosed before anyone applies.",
    about_safety_title: "3. Safety and Community Standards",
    about_safety_text: "Farmers on AgriSetu are encouraged to provide clean drinking water, emergency first aid, and fair working hours. Mutual reviews establish accountability, while administrative moderation protects community trust.",
    btn_explore_jobs: "Explore Open Jobs",
    btn_view_privacy: "View Privacy Guidelines",

    // Dashboard Farmer
    dash_farmer_title: "Farmer Dashboard",
    dash_total_jobs: "Total Jobs Posted",
    dash_active_jobs: "Active Open Jobs",
    dash_applications_received: "Applications Received",
    dash_confirmed_workers: "Confirmed Workers",
    dash_remaining_vacancies: "Remaining Vacancies",

    // Dashboard Labourer
    dash_labourer_title: "Labourer Dashboard",
    dash_total_apps: "Total Applications",
    dash_pending_apps: "Pending Review",
    dash_accepted_apps: "Accepted Applications",
    dash_active_assignments: "Active Assignments",
    dash_completed_assignments: "Completed Work",

    // Common Status Badges
    status_published: "Published",
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

    // Footer
    footer_desc: "AgriSetu is a dedicated agricultural employment platform connecting farmers with skilled workers across India through transparent wages and mutual respect.",
    footer_quick_links: "Quick Links",
    footer_support: "Support & Safety",
    footer_languages: "Languages",
    footer_rights: "All rights reserved.",
    footer_notice: "Empowering Rural Agriculture • Transparent & Fair Platform"
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
    hero_badge: "🌾 డిజిటల్ వ్యవసాయ ఉపాధి వేదిక",
    hero_title: "రైతులను నైపుణ్యం కలిగిన వ్యవసాయ శ్రామికులతో అనుసంధానించడం",
    hero_subtitle: "స్థానిక వ్యవసాయ పనులను పారదర్శకమైన రోజువారీ కూలీతో, దళారుల ప్రమేయం లేకుండా అందించే విశ్వసనీయ వేదిక.",
    btn_find_work: "వ్యవసాయ పనులను వెతకండి",
    btn_hire_workers: "కూలీలను నియమించండి / పని పోస్ట్ చేయండి",
    hero_search_title: "🔍 మీ దగ్గరలోని పనులను వెతకండి",

    // Crop Work Categories
    cat_all: "అన్ని రకాల పనులు",
    cat_harvesting: "వరి / పంట కోత (Harvesting)",
    cat_sowing: "విత్తనం నాటుట (Sowing)",
    cat_ploughing: "దుక్కి దున్నుట (Ploughing)",
    cat_weeding: "కలుపు తీయుట (Weeding)",
    cat_irrigation: "నీటిపారుదల పనులు (Irrigation)",
    cat_planting: "నాట్లు వేయుట (Planting)",
    cat_fertilizer_application: "ఎరువులు వేయుట (Fertilizer)",
    cat_fruit_picking: "పండ్ల కోత (Fruit Picking)",
    cat_general_agricultural_labour: "సాధారణ వ్యవసాయ పనులు",

    // Filters and Search
    filter_category: "పని రకం",
    filter_location: "జిల్లా / గ్రామం",
    filter_location_placeholder: "ఉదా: గుంటూరు, నిజామాబాద్, తెనాలి...",
    filter_min_wage: "కనిష్ట రోజువారీ కూలీ (₹)",
    filter_max_wage: "గరిష్ట రోజువారీ కూలీ (₹)",
    filter_min_wage_placeholder: "కనిష్ట ₹",
    filter_sort: "క్రమబద్ధీకరించండి",
    sort_newest: "తాజా పనులు మొదట",
    sort_wage_desc: "కూలీ: ఎక్కువ నుండి తక్కువ",
    sort_wage_asc: "కూలీ: తక్కువ నుండి ఎక్కువ",
    sort_date: "ప్రారంభ తేదీ ప్రకారం",
    filter_meals: "భోజనం అందించబడుతుంది",
    filter_accommodation: "వసతి సదుపాయం",
    filter_any: "అన్నీ",
    filter_yes_meals: "అవును, భోజనం ఉంది",
    btn_search: "పనులను వెతకండి",
    btn_apply_filters: "ఫిల్టర్లు వర్తింపజేయండి",
    btn_reset: "రీసెట్",
    btn_clear_filters: "అన్ని ఫిల్టర్‌లను తొలగించండి",

    // Section Titles
    categories_section_title: "వ్యవసాయ పనుల విభాగాలు",
    categories_section_subtitle: "రైతుల అవసరాలకు తగిన అనుభవం ఉన్న శ్రామికులను అనుసంధానించే విభాగాలు.",
    how_it_works_title: "అగ్రిసేతు ఎలా పనిచేస్తుంది?",
    how_it_works_subtitle: "గ్రామీణ ఉపాధిని సులభతరం చేసే సరళమైన 4-దశల ప్రక్రియ.",
    how_farmer_title: "రైతుల కోసం",
    how_farmer_step1: "పని వివరాలు, కావలసిన కూలీల సంఖ్య మరియు రోజువారీ కూలీని నమోదు చేయండి.",
    how_farmer_step2: "స్థానిక శ్రామికుల నైపుణ్యాలు మరియు అనుభవాన్ని పరిశీలించండి.",
    how_farmer_step3: "పరిమితికి మించకుండా సరైన కూలీలను ఆమోదించండి.",
    how_farmer_step4: "పని పూర్తయిన తర్వాత ఫోన్ నంబర్లు చూసి పరస్పర సమీక్ష ఇవ్వండి.",

    how_labourer_title: "వ్యవసాయ శ్రామికుల కోసం",
    how_labourer_step1: "మీ గ్రామం మరియు జిల్లా పరిధిలోని వ్యవసాయ పనులను సులభంగా చూడండి.",
    how_labourer_step2: "మీ ఫోన్ నంబర్ గోప్యంగా ఉంచుతూ ఒకే క్లిక్‌తో దరఖాస్తు చేసుకోండి.",
    how_labourer_step3: "రైతు మీ దరఖాస్తును ఆమోదించిన వెంటనే నోటిఫికేషన్ పొందండి.",
    how_labourer_step4: "పని పూర్తి చేసి మంచి రేటింగ్ పొందడం ద్వారా ఎక్కువ పనులను పొందండి.",

    features_title: "అగ్రిసేతు ఎందుకు?",
    features_subtitle: "గోప్యత, పారదర్శకత మరియు బహుభాషా సౌలభ్యంతో వ్యవసాయ సమాజం కోసం ప్రత్యేకంగా రూపొందించబడింది.",
    feat_local_title: "స్థానిక ఉపాధి",
    feat_local_desc: "మీ స్వంత గ్రామం మరియు పరిసర ప్రాంతాలలో ఎలాంటి దళారీ కమీషన్ లేకుండా పని పొందండి.",
    feat_wage_title: "పారదర్శక కూలీ",
    feat_wage_desc: "రోజువారీ లేదా గంటల కూలీ, భోజన సౌకర్యాల వివరాలు ముందుగానే తెలుసుకోండి.",
    feat_privacy_title: "సంప్రదింపుల గోప్యత",
    feat_privacy_desc: "మీ దరఖాస్తు ఆమోదించబడే వరకు మీ మొబైల్ నంబర్ ఇతరులకు కనిపించదు.",
    feat_allocation_title: "ఖచ్చితమైన కూలీల కేటాయింపు",
    feat_allocation_desc: "అవసరమైన కూలీల సంఖ్య మించకుండా సరైన సాంకేతిక నియంత్రణ ఉంటుంది.",

    featured_jobs_title: "ఇటీవలి వ్యవసాయ పనులు",
    featured_jobs_subtitle: "ధృవీకరించబడిన రైతులు నేరుగా పోస్ట్ చేసిన ప్రత్యక్ష వ్యవసాయ పనులు.",
    view_all_jobs: "అన్ని పనులను చూడండి →",

    // Mission Section
    mission_title: "మా వ్యవసాయ ఉపాధి లక్ష్యం",
    mission_text: "అగ్రిసేతు పంట కాలంలో కూలీల కొరతను ఎదుర్కొంటున్న రైతులను, ఉపాధి కోసం చూస్తున్న శ్రామికులతో నేరుగా కలుపుతుంది. దళారులను తొలగించి, తెలుగు, హిందీ మరియు ఆంగ్ల భాషలలో అందుబాటులో ఉంటూ, గోప్యతా రక్షణతో గ్రామీణ జీవనోపాధిని పెంపొందిస్తుంది.",
    btn_read_mission: "అగ్రిసేతు గురించి మరింత తెలుసుకోండి",
    btn_safety_standards: "రక్షణ & గోప్యతా ప్రమాణాలు",

    // Job Card Labels
    jobs_page_title: "వ్యవసాయ పనులను వెతకండి",
    jobs_page_subtitle: "ధృవీకరించబడిన రైతులు నేరుగా పోస్ట్ చేసిన వ్యవసాయ పనులను పరిశీలించండి.",
    label_wage: "కూలీ",
    card_per_day: "/ రోజుకు",
    card_per_hr: "/ గంటకు",
    label_location: "ప్రాంతం",
    label_dates: "తేదీలు",
    label_vacancies: "ఖాళీలు",
    label_capacity: "సామర్థ్యం:",
    label_workers: "శ్రామికులు",
    card_full: "పూర్తయింది (0 ఖాళీలు)",
    card_vacancies_open: "ఖాళీలు అందుబాటులో ఉన్నాయి",
    card_meals: "భోజనం అందించబడుతుంది",
    card_transport: "రవాణా సదుపాయం ఉంది",
    card_accommodation: "వసతి కల్పించబడుతుంది",
    btn_view_details: "వివరాలు చూడండి",
    btn_apply_now: "దరఖాస్తు చేసుకోండి",
    btn_applied: "దరఖాస్తు చేశారు",
    btn_save: "☆ సేవ్",
    btn_saved: "★ సేవ్ చేయబడింది",
    btn_fully_staffed: "కూలీలు భర్తీ అయ్యారు",

    // Loading & Empty States
    jobs_loading: "వ్యవసాయ పనుల వివరాలు లోడ్ అవుతున్నాయి...",
    jobs_matching_loading: "సరిపోలే పనులు లోడ్ అవుతున్నాయి...",
    jobs_empty_title: "సరిపోలే వ్యవసాయ పనులు ఏవీ కనుగొనబడలేదు",
    jobs_empty_desc: "మీ జిల్లా లేదా కూలీ ఫిల్టర్‌లను మార్చి మళ్ళీ ప్రయత్నించండి.",
    jobs_error_title: "పనులను లోడ్ చేయడంలో విఫలమైంది",
    jobs_error_desc: "ప్రస్తుతం పనులను పొందడం సాధ్యం కాలేదు. దయచేసి మళ్ళీ ప్రయత్నించండి.",
    btn_retry: "మళ్ళీ ప్రయత్నించండి",
    no_jobs_posted_yet: "ప్రస్తుతం డేటాబేస్‌లో పనులేవీ నమోదు కాలేదు.",
    btn_post_first_job: "రైతుగా మొదటి పనిని నమోదు చేయండి",

    // Apply Modal
    modal_apply_title: "వ్యవసాయ పని కోసం దరఖాస్తు",
    privacy_notice_title: "సంప్రదింపుల గోప్యత:",
    privacy_notice_text: "రైతు మీ దరఖాస్తును ఆమోదించే వరకు మీ మొబైల్ నంబర్ వారికి కనిపించదు.",
    label_cover_note: "సందేశం / అనుభవ వివరాలు (ఐచ్ఛికం)",
    placeholder_cover_note: "ఈ పంటలో మీ అనుభవం, మీతో పాటు తీసుకురాగల పరికరాలు లేదా పని దినాల లభ్యత తెలపండి...",
    btn_cancel: "రద్దు చేయండి",
    btn_submit_app: "దరఖాస్తు సమర్పించండి",
    btn_submitting: "సమర్పిస్తోంది...",
    app_submitted_success: "మీ దరఖాస్తు విజయవంతంగా సమర్పించబడింది! రైతుకు సమాచారం పంపబడింది.",
    app_submitted_error: "దరఖాస్తు సమర్పించడంలో లోపం ఏర్పడింది. దయచేసి మళ్ళీ ప్రయత్నించండి.",

    // Login Page
    login_title: "అగ్రిసేతు లాగిన్",
    login_subtitle: "రైతులను కలుపుతూ • శ్రామికులకు సాధికారత కల్పిస్తూ",
    login_banner_quote: "ప్రత్యక్ష అనుసంధానం మరియు పారదర్శక కూలీతో గ్రామీణ జీవనోపాధిని మెరుగుపరుస్తున్నాము.",
    login_role_guide_title: "పాత్ర సమాచారం",
    role_farmer_name: "రైతు",
    role_labourer_name: "శ్రామికుడు",
    role_admin_name: "అడ్మిన్",
    role_farmer_desc: "రైతులు కొత్త పనులను పోస్ట్ చేయవచ్చు, కూలీలను నిర్ణయించవచ్చు మరియు దరఖాస్తులను ఆమోదించవచ్చు.",
    role_labourer_desc: "వ్యవసాయ శ్రామికులు తమ గ్రామంలోని పనులను వెతకవచ్చు, సులభంగా దరఖాస్తు చేసుకోవచ్చు మరియు పని వివరాలను చూడవచ్చు.",
    role_admin_desc: "అడ్మినిస్ట్రేటర్లు వేదిక వినియోగదారులను మరియు పనులను పర్యవేక్షిస్తారు.",
    role_login_tip: "పరీక్ష సమాచారం: మీ ఖాతా వివరాలతో లాగిన్ అవ్వండి.",
    label_email: "ఇమెయిల్ చిరునామా",
    label_password: "పాస్‌వర్డ్",
    placeholder_email: "పేరు@ఉదాహరణ.com",
    placeholder_password: "••••••••",
    btn_logging_in: "లాగిన్ అవుతోంది...",
    no_account: "ఖాతా లేదా? ఇక్కడ నమోదు చేసుకోండి",
    have_account: "ఇప్పటికే ఖాతా ఉందా? లాగిన్ అవ్వండి",

    // Registration Page
    register_title: "అగ్రిసేతు ఖాతా తెరవండి",
    label_full_name: "పూర్తి పేరు",
    placeholder_full_name: "ఉదా: రమేష్ కుమార్",
    label_phone: "మొబైల్ నంబర్",
    placeholder_phone: "+91 98480 12345",
    label_confirm_password: "పాస్‌వర్డ్ ధృవీకరించండి",
    label_role: "రిజిస్ట్రేషన్ రకం:",
    role_farmer_radio: "రైతు (కూలీల కోసం చూస్తున్నాను)",
    role_labourer_radio: "వ్యవసాయ శ్రామికుడు (పని కోసం చూస్తున్నాను)",
    label_preferred_language: "ఇష్టపడే భాష",
    label_village: "గ్రామం / ఊరు",
    placeholder_village: "ఉదా: తెనాలి",
    label_district: "జిల్లా",
    placeholder_district: "ఉదా: గుంటూరు",
    label_state: "రాష్ట్రం",
    placeholder_state: "ఉదా: ఆంధ్రప్రదేశ్",
    label_privacy_consent: "నేను గోప్యతా విధానం మరియు నిబంధనలను అంగీకరిస్తున్నాను",
    btn_register: "ఖాతా సృష్టించండి",
    btn_registering: "నమోదు అవుతోంది...",

    // About Page (Professional)
    about_badge: "వ్యవసాయ ఉపాధి వేదిక",
    about_hero_title: "అగ్రిసేతు గురించి",
    about_hero_subtitle: "రైతులను కలుపుతూ • శ్రామికులకు సాధికారత కల్పిస్తూ",
    about_intro_title: "1. నేపథ్యం & వ్యవసాయ లక్ష్యం",
    about_intro_p1: "వ్యవసాయం భారతదేశ గ్రామీణ రంగానికి జీవనాధారం. అయినప్పటికీ పంట కోత మరియు నాట్ల సమయాల్లో రైతులకు కూలీల కొరత తీవ్ర సమస్యగా మారుతుంది. అదే సమయంలో స్థానిక శ్రామికులకు పనుల సమాచారం లేకపోవడం వల్ల ఉపాధి లభించదు.",
    about_intro_p2: "ఈ సమస్యను ఆసరాగా చేసుకుని మధ్యవర్తులు శ్రామికుల కష్టార్జితంలో అధిక శాతం కమీషన్‌గా తీసుకుంటారు.",
    about_intro_p3: "అగ్రిసేతు రైతులను మరియు వ్యవసాయ శ్రామికులను నేరుగా అనుసంధానించే పారదర్శక వేదికను అందిస్తుంది.",
    about_prin_title: "2. వేదిక ముఖ్య సాంకేతిక సూత్రాలు",
    about_prin_1_title: "ఖచ్చితమైన కూలీల కేటాయింపు:",
    about_prin_1_text: "అవసరమైన కూలీల సంఖ్య మించకుండా డేటాబేస్ స్థాయిలో కచ్చితమైన నియంత్రణ ఉంటుంది.",
    about_prin_2_title: "సంప్రదింపుల గోప్యత:",
    about_prin_2_text: "దరఖాస్తు ఖరారయ్యే వరకు వ్యక్తిగత ఫోన్ నంబర్లు ఇతరులకు కనిపించకుండా రక్షించబడతాయి.",
    about_prin_3_title: "బహుభాషా సౌలభ్యం:",
    about_prin_3_text: "గ్రామీణ శ్రామికుల సౌకర్యార్థం తెలుగు, హిందీ మరియు ఆంగ్ల భాషలలో పూర్తి సమాచారం లభిస్తుంది.",
    about_prin_4_title: "పారదర్శక నిబంధనలు:",
    about_prin_4_text: "రోజువారీ కూలీ, భోజన సౌకర్యాలు మరియు పని గంటలు ముందుగానే స్పష్టంగా చూపబడతాయి.",
    about_safety_title: "3. రక్షణ మరియు సమాజ ప్రమాణాలు",
    about_safety_text: "రైతులు పరిశుభ్రమైన తాగునీరు మరియు ప్రథమ చికిత్స కిట్లు ఏర్పాటు చేయాలి. పరస్పర సమీక్షలు వేదికపై నమ్మకాన్ని పెంచుతాయి.",
    btn_explore_jobs: "పనులను చూడండి",
    btn_view_privacy: "గోప్యతా మార్గదర్శకాలు",

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

    // Common Status Badges
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

    // Footer
    footer_desc: "అగ్రిసేతు అనేది రైతులను నైపుణ్యం కలిగిన శ్రామికులతో నేరుగా అనుసంధానించే పారదర్శక వ్యవసాయ ఉపాధి వేదిక.",
    footer_quick_links: "ముఖ్యమైన లింకులు",
    footer_support: "సహాయం & రక్షణ",
    footer_languages: "భాషలు",
    footer_rights: "సర్వ హక్కులు ప్రత్యేకించబడ్డాయి.",
    footer_notice: "గ్రామీణ వ్యవసాయ సాధికారత • పారదర్శక మరియు న్యాయమైన వేదిక"
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
    hero_badge: "🌾 डिजिटल कृषि रोजगार मंच",
    hero_title: "किसानों को कुशल कृषि मजदूरों से जोड़ना",
    hero_subtitle: "पारदर्शी दैनिक मजदूरी, बिना किसी बिचौलिए के स्थानीय कृषि रोजगार प्रदान करने वाला विश्वसनीय मंच।",
    btn_find_work: "कृषि कार्य खोजें",
    btn_hire_workers: "कार्य पोस्ट करें / मजदूर खोजें",
    hero_search_title: "🔍 अपने आस-पास काम खोजें",

    // Crop Work Categories
    cat_all: "सभी श्रेणियां",
    cat_harvesting: "फसल कटाई (Harvesting)",
    cat_sowing: "बीज बुवाई (Sowing)",
    cat_ploughing: "खेत जुताई (Ploughing)",
    cat_weeding: "निराई / खरपतवार (Weeding)",
    cat_irrigation: "सिंचाई कार्य (Irrigation)",
    cat_planting: "पौध रोपाई (Planting)",
    cat_fertilizer_application: "खाद छिड़काव (Fertilizer)",
    cat_fruit_picking: "फल तुड़ाई (Fruit Picking)",
    cat_general_agricultural_labour: "सामान्य कृषि कार्य",

    // Filters and Search
    filter_category: "कार्य श्रेणी",
    filter_location: "जिला / गांव",
    filter_location_placeholder: "उदा: निजामाबाद, गुंटूर, आर्मूर...",
    filter_min_wage: "न्यूनतम दैनिक मजदूरी (₹)",
    filter_max_wage: "अधिकतम दैनिक मजदूरी (₹)",
    filter_min_wage_placeholder: "न्यूनतम ₹",
    filter_sort: "क्रमबद्ध करें",
    sort_newest: "नवीनतम पहले",
    sort_wage_desc: "मजदूरी: अधिक से कम",
    sort_wage_asc: "मजदूरी: कम से अधिक",
    sort_date: "प्रारंभिक तिथि अनुसार",
    filter_meals: "भोजन की व्यवस्था",
    filter_accommodation: "आवास सुविधा",
    filter_any: "कोई भी",
    filter_yes_meals: "हां, भोजन उपलब्ध",
    btn_search: "काम खोजें",
    btn_apply_filters: "फ़िल्टर लागू करें",
    btn_reset: "रीसेट",
    btn_clear_filters: "सभी फ़िल्टर साफ़ करें",

    // Section Titles
    categories_section_title: "कृषि कार्य श्रेणियां",
    categories_section_subtitle: "अनुभवी मजदूरों को किसानों की आवश्यकताओं से जोड़ने वाले कृषि कार्य।",
    how_it_works_title: "एग्रीसेतु कैसे काम करता है?",
    how_it_works_subtitle: "ग्रामीण रोजगार को सशक्त बनाने वाली सरल 4-चरणीय प्रक्रिया।",
    how_farmer_title: "किसानों के लिए",
    how_farmer_step1: "काम का विवरण, आवश्यक मजदूरों की संख्या और दैनिक मजदूरी दर्ज करें।",
    how_farmer_step2: "स्थानीय श्रमिकों के कौशल और अनुभव की समीक्षा करें।",
    how_farmer_step3: "मजदूरों को स्वीकार करें, क्षमता से अधिक बुकिंग नहीं होगी।",
    how_farmer_step4: "काम पूरा होने के बाद संपर्क साझा करें और रेटिंग दें।",

    how_labourer_title: "कृषि मजदूरों के लिए",
    how_labourer_step1: "अपने गांव और जिले में कृषि कार्यों की खोज करें।",
    how_labourer_step2: "अपना फोन नंबर गुप्त रखते हुए 1-क्लिक में आवेदन करें।",
    how_labourer_step3: "आवेदन स्वीकार होने पर तुरंत सूचना प्राप्त करें।",
    how_labourer_step4: "सीधे किसान से संपर्क करें, काम पूरा करें और अच्छी रेटिंग पाएं।",

    features_title: "एग्रीसेतु क्यों?",
    features_subtitle: "गोपनीयता, पारदर्शिता और बहुभाषी सुविधा के साथ कृषि समुदाय के लिए विशेष मंच।",
    feat_local_title: "स्थानीय रोजगार",
    feat_local_desc: "बिना किसी दलाली के अपने गांव और जिले में सीधे रोजगार पाएं।",
    feat_wage_title: "पारदर्शी मजदूरी",
    feat_wage_desc: "दैनिक मजदूरी, भोजन व्यवस्था और काम के घंटे पहले से स्पष्ट देखें।",
    feat_privacy_title: "संपर्क गोपनीयता",
    feat_privacy_desc: "आवेदन स्वीकृत होने से पहले फोन नंबर सुरक्षित रहता है।",
    feat_allocation_title: "सटीक मजदूर आवंटन",
    feat_allocation_desc: "आवश्यक मजदूरों से अधिक की बुकिंग रोकने की सुरक्षित प्रणाली।",

    featured_jobs_title: "हाल ही में पोस्ट किए गए कृषि कार्य",
    featured_jobs_subtitle: "सत्यापित किसानों द्वारा पोस्ट किए गए वर्तमान खुले कार्य।",
    view_all_jobs: "सभी काम देखें →",

    // Mission Section
    mission_title: "हमारा कृषि रोजगार मिशन",
    mission_text: "एग्रीसेतु फसल के मौसम में मजदूरों की कमी से जूझ रहे किसानों को सीधे उन कृषि श्रमिकों से जोड़ता है जो रोजगार की तलाश में हैं। बिचौलियों को हटाकर, हिंदी, तेलुगु और अंग्रेजी में उपलब्ध होकर, यह मंच ग्रामीण आजीविका को सशक्त बनाता है।",
    btn_read_mission: "एग्रीसेतु के बारे में और जानें",
    btn_safety_standards: "सुरक्षा एवं गोपनीयता मानक",

    // Job Card Labels
    jobs_page_title: "कृषि कार्य खोजें",
    jobs_page_subtitle: "सत्यापित किसानों द्वारा सीधे पोस्ट किए गए कृषि कार्यों को देखें।",
    label_wage: "मजदूरी",
    card_per_day: "/ दिन",
    card_per_hr: "/ घंटा",
    label_location: "स्थान",
    label_dates: "तिथियां",
    label_vacancies: "रिक्तियां",
    label_capacity: "क्षमता:",
    label_workers: "मजदूर",
    card_full: "पूर्ण (0 रिक्तियां)",
    card_vacancies_open: "रिक्तियां उपलब्ध हैं",
    card_meals: "भोजन उपलब्ध",
    card_transport: "परिवहन उपलब्ध",
    card_accommodation: "आवास उपलब्ध",
    btn_view_details: "विवरण देखें",
    btn_apply_now: "आवेदन करें",
    btn_applied: "आवेदन किया गया",
    btn_save: "☆ सहेजें",
    btn_saved: "★ सहेजा गया",
    btn_fully_staffed: "पूर्णतः आवंटित",

    // Loading & Empty States
    jobs_loading: "कृषि कार्य लोड हो रहे हैं...",
    jobs_matching_loading: "मेल खाने वाले कार्य लोड हो रहे हैं...",
    jobs_empty_title: "कोई मेल खाने वाला कृषि कार्य नहीं मिला",
    jobs_empty_desc: "कृपया अपना स्थान, मजदूरी या कार्य श्रेणी बदलकर पुनः प्रयास करें।",
    jobs_error_title: "कार्य लोड करने में विफल",
    jobs_error_desc: "वर्तमान में कार्य लोड नहीं हो सके। कृपया पुनः प्रयास करें।",
    btn_retry: "पुनः प्रयास करें",
    no_jobs_posted_yet: "डेटाबेस में अभी कोई कार्य पोस्ट नहीं किया गया है।",
    btn_post_first_job: "किसान के रूप में पहला कार्य पोस्ट करें",

    // Apply Modal
    modal_apply_title: "कृषि कार्य के लिए आवेदन",
    privacy_notice_title: "संपर्क गोपनीयता:",
    privacy_notice_text: "किसान द्वारा आवेदन स्वीकार करने तक आपका मोबाइल नंबर गुप्त रहेगा।",
    label_cover_note: "संदेश / कार्य अनुभव (वैकल्पिक)",
    placeholder_cover_note: "इस फसल में अपना अनुभव, उपकरण या काम के दिनों की उपलब्धता बताएं...",
    btn_cancel: "रद्द करें",
    btn_submit_app: "आवेदन जमा करें",
    btn_submitting: "जमा हो रहा है...",
    app_submitted_success: "आपका आवेदन सफलतापूर्वक जमा हो गया! किसान को सूचित कर दिया गया है।",
    app_submitted_error: "आवेदन जमा करने में त्रुटि। कृपया पुनः प्रयास करें।",

    // Login Page
    login_title: "एग्रीसेतु में लॉग इन करें",
    login_subtitle: "किसानों को जोड़ना • मजदूरों को सशक्त बनाना",
    login_banner_quote: "सीधे संपर्क और पारदर्शी मजदूरी के माध्यम से ग्रामीण समुदाय को सशक्त बनाना।",
    login_role_guide_title: "भूमिका मार्गदर्शन",
    role_farmer_name: "किसान",
    role_labourer_name: "मजदूर",
    role_admin_name: "व्यवस्थापक",
    role_farmer_desc: "किसान नए कृषि कार्यों को पोस्ट कर सकते हैं, मजदूरी तय कर सकते हैं और आवेदनों की समीक्षा कर सकते हैं।",
    role_labourer_desc: "कृषि मजदूर अपने क्षेत्र में काम खोज सकते हैं, सीधे आवेदन कर सकते हैं और कार्य स्थिति देख सकते हैं।",
    role_admin_desc: "व्यवस्थापक मंच के उपयोगकर्ताओं, कार्यों और रिपोर्टों का प्रबंधन करते हैं।",
    role_login_tip: "परीक्षण सुझाव: अपने पंजीकृत क्रेडेंशियल्स दर्ज करके लॉग इन करें।",
    label_email: "ईमेल पता",
    label_password: "पासवर्ड",
    placeholder_email: "name@example.com",
    placeholder_password: "••••••••",
    btn_logging_in: "लॉग इन हो रहा है...",
    no_account: "खाता नहीं है? यहां पंजीकरण करें",
    have_account: "पहले से खाता है? लॉग इन करें",

    // Registration Page
    register_title: "एग्रीसेतु खाता बनाएं",
    label_full_name: "पूरा नाम",
    placeholder_full_name: "उदा: सुरेश पटेल",
    label_phone: "मोबाइल नंबर",
    placeholder_phone: "+91 98480 12345",
    label_confirm_password: "पासवर्ड की पुष्टि करें",
    label_role: "पंजीकरण प्रकार:",
    role_farmer_radio: "किसान (मजदूरों की आवश्यकता है)",
    role_labourer_radio: "कृषि मजदूर (काम की तलाश है)",
    label_preferred_language: "पसंदीदा भाषा",
    label_village: "गांव / शहर",
    placeholder_village: "उदा: आर्मूर",
    label_district: "जिला",
    placeholder_district: "उदा: निजामाबाद",
    label_state: "राज्य",
    placeholder_state: "उदा: तेलंगाना",
    label_privacy_consent: "मैं गोपनीयता नीति और समुदाय की शर्तों से सहमत हूं",
    btn_register: "खाता बनाएं",
    btn_registering: "पंजीकरण हो रहा है...",

    // About Page (Professional)
    about_badge: "कृषि रोजगार मंच",
    about_hero_title: "एग्रीसेतु के बारे में",
    about_hero_subtitle: "किसानों को जोड़ना • मजदूरों को सशक्त बनाना",
    about_intro_title: "1. पृष्ठभूमि एवं कृषि मिशन",
    about_intro_p1: "कृषि भारत की आत्मा है, लेकिन फसल कटाई और बुवाई के समय किसानों को मजदूरों की भारी कमी का सामना करना पड़ता है। साथ ही, कृषि श्रमिकों को आस-पास के खुले कार्यों की जानकारी नहीं मिल पाती।",
    about_intro_p2: "पारंपरिक रूप से अनियंत्रित बिचौलिए मजदूरों की मजदूरी का बड़ा हिस्सा कमीशन के रूप में ले लेते हैं।",
    about_intro_p3: "एग्रीसेतु किसानों और मजदूरों को सीधे जोड़ने वाला पारदर्शी मंच प्रदान करता है।",
    about_prin_title: "2. मंच के मुख्य तकनीकी सिद्धांत",
    about_prin_1_title: "सटीक मजदूर आवंटन:",
    about_prin_1_text: "आवश्यक मजदूरों से अधिक की बुकिंग रोकने के लिए डेटाबेस स्तर पर सुरक्षित आवंटन प्रणाली।",
    about_prin_2_title: "संपर्क गोपनीयता:",
    about_prin_2_text: "आवेदन पुष्ट होने तक व्यक्तिगत फोन नंबर सुरक्षित और अप्रदर्शित रहते हैं।",
    about_prin_3_title: "बहुभाषी पहुंच:",
    about_prin_3_text: "हिंदी, तेलुगु और अंग्रेजी में पूर्ण उपलब्धता ताकि ग्रामीण मजदूर अपनी भाषा में काम कर सकें।",
    about_prin_4_title: "पारदर्शी शर्तें:",
    about_prin_4_text: "दैनिक मजदूरी, भोजन व्यवस्था और समय पहले से स्पष्ट रूप से बताए जाते हैं।",
    about_safety_title: "3. सुरक्षा एवं सामुदायिक मानक",
    about_safety_text: "किसान स्वच्छ पेयजल और प्राथमिक चिकित्सा किट उपलब्ध कराएं। आपसी समीक्षा से विश्वास बढ़ता है।",
    btn_explore_jobs: "उपलब्ध काम देखें",
    btn_view_privacy: "गोपनीयता दिशानिर्देश",

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

    // Common Status Badges
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

    // Footer
    footer_desc: "एग्रीसेतु किसानों और कृषि श्रमिकों को सीधे जोड़ने वाला पारदर्शी रोजगार मंच है।",
    footer_quick_links: "त्वरित लिंक",
    footer_support: "सहायता एवं सुरक्षा",
    footer_languages: "भाषाएं",
    footer_rights: "सर्वाधिकार सुरक्षित।",
    footer_notice: "ग्रामीण कृषि सशक्तिकरण • पारदर्शी एवं निष्पक्ष मंच"
  }
};

let currentLanguage = localStorage.getItem('agrisetu_lang') || 'en';

function setLanguage(lang) {
  if (!translations[lang]) lang = 'en';
  currentLanguage = lang;
  localStorage.setItem('agrisetu_lang', lang);

  // Set document language attribute
  document.documentElement.lang = lang;

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

  // Apply title/aria-label translations
  document.querySelectorAll('[data-i18n-title]').forEach(el => {
    const key = el.getAttribute('data-i18n-title');
    if (translations[lang] && translations[lang][key]) {
      el.setAttribute('title', translations[lang][key]);
    }
  });

  // Dispatch custom event for dynamic components to re-render in new language
  window.dispatchEvent(new CustomEvent('languageChanged', { detail: { language: lang } }));

  // If user is authenticated, sync preferred language with SQL Server backend
  if (window.api && window.auth && window.auth.isLoggedIn()) {
    window.api.put('/api/users/language', { language: lang }).catch(() => {});
  }
}

function t(key, fallback = '') {
  if (translations[currentLanguage] && translations[currentLanguage][key] !== undefined) {
    return translations[currentLanguage][key];
  }
  if (translations.en && translations.en[key] !== undefined) {
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
