// home.js - Interactive features for the home page

(function() {
    'use strict';

    // Profile dropdown toggle
    function setupProfileDropdown() {
        const profileBtn = document.getElementById('profileMenuBtn');
        const dropdownMenu = document.getElementById('profileMenu');

        if (!profileBtn || !dropdownMenu) return;

        profileBtn.addEventListener('click', (e) => {
            e.stopPropagation();
            dropdownMenu.classList.toggle('show');
        });

        // Close dropdown when clicking outside
        document.addEventListener('click', (e) => {
            if (!profileBtn.contains(e.target) && !dropdownMenu.contains(e.target)) {
                dropdownMenu.classList.remove('show');
            }
        });

        // Close on escape key
        document.addEventListener('keydown', (e) => {
            if (e.key === 'Escape' && dropdownMenu.classList.contains('show')) {
                dropdownMenu.classList.remove('show');
                profileBtn.focus();
            }
        });
    }

    // Filter offers by type
    function setupOfferFilters() {
        const filterBtns = document.querySelectorAll('.filter-btn');
        const offerCards = document.querySelectorAll('.offer-card');

        if (filterBtns.length === 0 || offerCards.length === 0) return;

        filterBtns.forEach(btn => {
            btn.addEventListener('click', () => {
                const filter = btn.dataset.filter;

                // Update active state
                filterBtns.forEach(b => b.classList.remove('active'));
                btn.classList.add('active');

                // Filter offers
                offerCards.forEach(card => {
                    const offerType = card.querySelector('.offer-type');
                    if (!offerType) return;

                    const isTeach = offerType.classList.contains('type-teach');
                    const isRequest = offerType.classList.contains('type-request');

                    if (filter === 'all') {
                        card.style.display = '';
                    } else if (filter === 'teach' && isTeach) {
                        card.style.display = '';
                    } else if (filter === 'request' && isRequest) {
                        card.style.display = '';
                    } else {
                        card.style.display = 'none';
                    }
                });
            });
        });
    }

    // Search functionality
    function setupSearch() {
        const searchInput = document.getElementById('searchInput');
        if (!searchInput) return;

        let searchTimeout;

        searchInput.addEventListener('input', (e) => {
            clearTimeout(searchTimeout);

            searchTimeout = setTimeout(() => {
                const query = e.target.value.toLowerCase().trim();
                filterOffersBySearch(query);
            }, 300); // Debounce for 300ms
        });
    }

    function filterOffersBySearch(query) {
        const offerCards = document.querySelectorAll('.offer-card');

        if (!query) {
            offerCards.forEach(card => card.style.display = '');
            return;
        }

        offerCards.forEach(card => {
            const title = card.querySelector('.offer-title')?.textContent.toLowerCase() || '';
            const description = card.querySelector('.offer-description')?.textContent.toLowerCase() || '';
            const author = card.querySelector('.author-name')?.textContent.toLowerCase() || '';

            if (title.includes(query) || description.includes(query) || author.includes(query)) {
                card.style.display = '';
            } else {
                card.style.display = 'none';
            }
        });
    }

    // Save/Like button functionality
    function setupSaveButtons() {
        const saveButtons = document.querySelectorAll('.btn-outline.btn-small');

        saveButtons.forEach(btn => {
            const icon = btn.querySelector('.fa-heart');
            if (!icon) return;

            btn.addEventListener('click', (e) => {
                e.preventDefault();

                // Toggle saved state
                if (icon.classList.contains('fas')) {
                    icon.classList.remove('fas');
                    icon.classList.add('far');
                    btn.style.color = '';
                } else {
                    icon.classList.remove('far');
                    icon.classList.add('fas');
                    btn.style.color = '#e63946';
                }
            });
        });
    }

    // Smooth scroll to top
    function setupScrollToTop() {
        let lastScrollTop = 0;
        const navbar = document.querySelector('.navbar');

        if (!navbar) return;

        window.addEventListener('scroll', () => {
            const scrollTop = window.pageYOffset || document.documentElement.scrollTop;

            if (scrollTop > lastScrollTop && scrollTop > 100) {
                // Scrolling down
                navbar.style.transform = 'translateY(-100%)';
            } else {
                // Scrolling up
                navbar.style.transform = 'translateY(0)';
            }

            lastScrollTop = scrollTop <= 0 ? 0 : scrollTop;
        }, false);
    }

    // Animate cards on scroll
    function setupScrollAnimations() {
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.style.opacity = '1';
                    entry.target.style.transform = 'translateY(0)';
                }
            });
        }, observerOptions);

        const cards = document.querySelectorAll('.offer-card');
        cards.forEach((card, index) => {
            card.style.opacity = '0';
            card.style.transform = 'translateY(20px)';
            card.style.transition = `all 0.5s ease ${index * 0.1}s`;
            observer.observe(card);
        });
    }

    // Initialize all features
    function init() {
        setupProfileDropdown();
        setupOfferFilters();
        setupSearch();
        setupSaveButtons();
        setupScrollToTop();
        setupScrollAnimations();
    }

    // Wait for DOM to be ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', init);
    } else {
        init();
    }

    // Export for debugging
    window.__HomePageDebug = {
        filterOffersBySearch,
        setupProfileDropdown,
        setupOfferFilters
    };
})();