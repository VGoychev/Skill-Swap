// password-toggle.js
// Reusable password visibility toggle + optional confirm-match validation.
// Auto-inits with defaults but exposes window.__PasswordToggle.init for manual use.

(function () {
    /**
     * Create a toggle for an input/icon pair.
     * @param {HTMLInputElement} inputEl
     * @param {HTMLElement} iconEl
     */
    function setupToggle(inputEl, iconEl) {
        if (!inputEl || !iconEl) return;

        function setHiddenState() {
            inputEl.type = 'password';
            iconEl.classList.remove('fa-eye-slash');
            iconEl.classList.add('fa-eye');
            iconEl.setAttribute('aria-label', 'Show password');
            iconEl.title = 'Show password';
            iconEl.setAttribute('aria-pressed', 'false');
        }
        function setVisibleState() {
            inputEl.type = 'text';
            iconEl.classList.remove('fa-eye');
            iconEl.classList.add('fa-eye-slash');
            iconEl.setAttribute('aria-label', 'Hide password');
            iconEl.title = 'Hide password';
            iconEl.setAttribute('aria-pressed', 'true');
        }

        iconEl.addEventListener('click', () => {
            if (inputEl.type === 'password') setVisibleState();
            else setHiddenState();
        });

        iconEl.addEventListener('keydown', (e) => {
            if (e.key === 'Enter' || e.key === ' ' || e.key === 'Spacebar') {
                e.preventDefault();
                iconEl.click();
            }
        });

        // initialize hidden by default
        setHiddenState();
    }

    /**
     * Live confirm-password match validation.
     * @param {HTMLInputElement} pwdEl
     * @param {HTMLInputElement} confirmEl
     * @param {HTMLElement|null} errorEl
     * @param {HTMLFormElement|null} formEl
     */
    function setupMatchValidation(pwdEl, confirmEl, errorEl, formEl) {
        if (!confirmEl) return;

        function validateMatch() {
            const pwd = pwdEl ? pwdEl.value : '';
            const rep = confirmEl ? confirmEl.value : '';
            const ok = !rep || pwd === rep; // only show when user starts typing confirm
            if (!ok) {
                confirmEl.setCustomValidity('Passwords do not match');
                if (errorEl) errorEl.style.display = '';
            } else {
                confirmEl.setCustomValidity('');
                if (errorEl) errorEl.style.display = 'none';
            }
        }

        if (pwdEl) pwdEl.addEventListener('input', validateMatch);
        confirmEl.addEventListener('input', validateMatch);
        if (formEl) formEl.addEventListener('submit', validateMatch);
    }

    /**
     * Initialize many toggle pairs and optional validation.
     * @param {Object} opts
     *   - pairs: Array<{ input: selector|element, icon: selector|element }>
     *   - match: { pwd: selector|element, confirm: selector|element, errorEl: selector|element (optional), form: selector|element (optional) }
     */
    function initPasswordToggle(opts = {}) {
        const pairs = Array.isArray(opts.pairs) ? opts.pairs : [];

        pairs.forEach(p => {
            const inputEl = (typeof p.input === 'string') ? document.querySelector(p.input) : p.input;
            const iconEl  = (typeof p.icon  === 'string') ? document.querySelector(p.icon)  : p.icon;
            setupToggle(inputEl, iconEl);
        });

        if (opts.match) {
            const pwdEl = (typeof opts.match.pwd === 'string') ? document.querySelector(opts.match.pwd) : opts.match.pwd;
            const confirmEl = (typeof opts.match.confirm === 'string') ? document.querySelector(opts.match.confirm) : opts.match.confirm;
            const errorEl = opts.match.errorEl ? ((typeof opts.match.errorEl === 'string') ? document.querySelector(opts.match.errorEl) : opts.match.errorEl) : null;
            const formEl = opts.match.form ? ((typeof opts.match.form === 'string') ? document.querySelector(opts.match.form) : opts.match.form) : null;
            setupMatchValidation(pwdEl, confirmEl, errorEl, formEl);
        }
    }


    document.addEventListener('DOMContentLoaded', () => {
        // Login default pair
        const loginPwd = document.getElementById('password');
        const loginIcon = document.getElementById('toggleIcon');

        // Register pair(s)
        const regPwd = document.getElementById('password');
        const regConfirm = document.getElementById('confirmPassword');
        const regIcon = document.getElementById('toggleIcon');
        const regIconConfirm = document.getElementById('toggleIconConfirm');
        const regForm = document.querySelector('form.register-form');
        const matchErrorEl = document.getElementById('matchError');

        // If both password and confirm are present -> register scenario
        if (regPwd && regConfirm) {
            const pairs = [];
            if (regIcon) pairs.push({ input: regPwd, icon: regIcon });
            if (regIconConfirm) pairs.push({ input: regConfirm, icon: regIconConfirm });
            initPasswordToggle({
                pairs,
                match: { pwd: regPwd, confirm: regConfirm, errorEl: matchErrorEl, form: regForm }
            });
            return;
        }

        // Else if only login password exists
        if (loginPwd && loginIcon) {
            initPasswordToggle({ pairs: [{ input: loginPwd, icon: loginIcon }] });
        }
    });

    window.__PasswordToggle = { init: initPasswordToggle };
})();
