document.addEventListener('DOMContentLoaded', function() {
    // Tab navigation functionality
    function activateTab(tabId) {
        // Hide all tab content
        const tabContents = document.querySelectorAll('.tab-pane');
        tabContents.forEach(tab => {
            tab.classList.remove('show', 'active');
        });

        // Deactivate all tabs
        const tabLinks = document.querySelectorAll('.nav-link');
        tabLinks.forEach(link => {
            link.classList.remove('active');
        });

        // Activate the selected tab
        const selectedTab = document.getElementById(tabId);
        if (selectedTab) {
            selectedTab.classList.add('show', 'active');
        }

        // Activate the corresponding tab link
        const selectedTabLink = document.querySelector(`[data-bs-target="#${tabId}"]`);
        if (selectedTabLink) {
            selectedTabLink.classList.add('active');
        }

        // Update URL hash for bookmarking
        window.location.hash = tabId;
    }

    // Initialize tabs from URL hash or default to first tab
    function initializeTabs() {
        let tabId = window.location.hash.substring(1);
        const tabPanes = document.querySelectorAll('.tab-pane');
        
        if (tabId && document.getElementById(tabId)) {
            activateTab(tabId);
        } else if (tabPanes.length > 0) {
            // Default to first tab if no hash or invalid hash
            tabId = tabPanes[0].id;
            activateTab(tabId);
        }
    }

    // Set up tab click event listeners
    const tabLinks = document.querySelectorAll('.nav-link[data-bs-toggle="tab"]');
    tabLinks.forEach(link => {
        link.addEventListener('click', function(e) {
            const targetId = this.getAttribute('data-bs-target').substring(1);
            activateTab(targetId);
            e.preventDefault();
        });
    });

    // Smooth scrolling for anchor links
    document.querySelectorAll('a[href^="#"]').forEach(anchor => {
        anchor.addEventListener('click', function(e) {
            const targetId = this.getAttribute('href').substring(1);
            const targetElement = document.getElementById(targetId);
            
            if (targetElement) {
                e.preventDefault();
                window.scrollTo({
                    top: targetElement.offsetTop - 70, // Offset for fixed navbar
                    behavior: 'smooth'
                });
                
                // If target is a tab, activate it
                if (targetElement.classList.contains('tab-pane')) {
                    activateTab(targetId);
                }
            }
        });
    });

    // Mobile menu toggle
    const navbarToggler = document.querySelector('.navbar-toggler');
    const navbarCollapse = document.querySelector('.navbar-collapse');
    
    if (navbarToggler && navbarCollapse) {
        navbarToggler.addEventListener('click', function() {
            navbarCollapse.classList.toggle('show');
        });
        
        // Close mobile menu when clicking outside or on a menu item
        document.addEventListener('click', function(e) {
            const isNavbarToggler = navbarToggler.contains(e.target);
            const isNavbarCollapse = navbarCollapse.contains(e.target);
            
            if (!isNavbarToggler && !isNavbarCollapse && navbarCollapse.classList.contains('show')) {
                navbarCollapse.classList.remove('show');
            }
        });
        
        // Close mobile menu when clicking on nav-link
        document.querySelectorAll('.navbar-nav .nav-link').forEach(link => {
            link.addEventListener('click', function() {
                if (window.innerWidth < 992) { // Bootstrap lg breakpoint
                    navbarCollapse.classList.remove('show');
                }
            });
        });
    }

    // Add active class to navbar links based on scroll position
    function setActiveNavLinkOnScroll() {
        const sections = document.querySelectorAll('section[id]');
        const scrollPosition = window.scrollY + 100; // 100px offset

        sections.forEach(section => {
            const sectionTop = section.offsetTop;
            const sectionHeight = section.offsetHeight;
            const sectionId = section.getAttribute('id');
            
            if (scrollPosition >= sectionTop && scrollPosition < sectionTop + sectionHeight) {
                document.querySelectorAll('.navbar-nav .nav-link').forEach(link => {
                    link.classList.remove('active');
                    if (link.getAttribute('href') === `#${sectionId}` || 
                        link.getAttribute('data-bs-target') === `#${sectionId}`) {
                        link.classList.add('active');
                    }
                });
            }
        });
    }

    // Back to top button functionality
    function setupBackToTopButton() {
        const backToTopButton = document.createElement('button');
        backToTopButton.innerHTML = '&uarr;';
        backToTopButton.classList.add('back-to-top');
        backToTopButton.setAttribute('title', 'Back to Top');
        document.body.appendChild(backToTopButton);

        backToTopButton.addEventListener('click', function() {
            window.scrollTo({
                top: 0,
                behavior: 'smooth'
            });
        });

        // Show/hide button based on scroll position
        window.addEventListener('scroll', function() {
            if (window.scrollY > 300) {
                backToTopButton.classList.add('visible');
            } else {
                backToTopButton.classList.remove('visible');
            }
        });
    }

    // Code highlighting for code examples
    function highlightCodeBlocks() {
        document.querySelectorAll('pre code').forEach(block => {
            block.classList.add('highlighted');
            
            // Add copy button to code blocks
            const copyButton = document.createElement('button');
            copyButton.innerHTML = 'Copy';
            copyButton.classList.add('copy-code-button');
            block.parentNode.appendChild(copyButton);
            
            copyButton.addEventListener('click', function() {
                const code = block.textContent;
                navigator.clipboard.writeText(code).then(function() {
                    copyButton.innerHTML = 'Copied!';
                    setTimeout(function() {
                        copyButton.innerHTML = 'Copy';
                    }, 2000);
                }).catch(function(err) {
                    console.error('Could not copy text: ', err);
                    copyButton.innerHTML = 'Error';
                    setTimeout(function() {
                        copyButton.innerHTML = 'Copy';
                    }, 2000);
                });
            });
        });
    }

    // Implement tooltip for components with the 'data-tooltip' attribute
    function initializeTooltips() {
        document.querySelectorAll('[data-tooltip]').forEach(element => {
            const tooltipText = element.getAttribute('data-tooltip');
            
            element.addEventListener('mouseenter', function(e) {
                const tooltip = document.createElement('div');
                tooltip.classList.add('custom-tooltip');
                tooltip.textContent = tooltipText;
                document.body.appendChild(tooltip);
                
                const rect = element.getBoundingClientRect();
                tooltip.style.left = rect.left + (rect.width / 2) - (tooltip.offsetWidth / 2) + 'px';
                tooltip.style.top = rect.bottom + 10 + 'px';
                
                tooltip.classList.add('active');
                
                element.addEventListener('mouseleave', function() {
                    tooltip.remove();
                }, { once: true });
            });
        });
    }

    // Initialize all functionality
    initializeTabs();
    setupBackToTopButton();
    highlightCodeBlocks();
    initializeTooltips();

    // Add scroll event listener
    window.addEventListener('scroll', setActiveNavLinkOnScroll);

    // Handle window resize events for responsive behavior
    let resizeTimeout;
    window.addEventListener('resize', function() {
        clearTimeout(resizeTimeout);
        resizeTimeout = setTimeout(function() {
            if (window.innerWidth >= 992 && navbarCollapse) {
                navbarCollapse.classList.remove('show');
            }
        }, 250);
    });
});

