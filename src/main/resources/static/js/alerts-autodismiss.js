// alerts-autodismiss.js

(function () {
    function autoDismissAlerts({ timeout = 4000, fadeDuration = 800 } = {}) {
        document.querySelectorAll('.alert').forEach(el => {
            // skip persistent alerts if they have data-persistent="true"
            if (el.dataset.persistent === "true") return;

            // schedule fade
            setTimeout(() => {
                el.style.transition = `opacity ${fadeDuration}ms ease`;
                el.style.opacity = '0';
                setTimeout(() => {
                    if (el.parentNode) el.parentNode.removeChild(el);
                }, fadeDuration);
            }, timeout);
        });
    }

    document.addEventListener('DOMContentLoaded', () => autoDismissAlerts());
    window.__AlertsAutoDismiss = { autoDismissAlerts };
})();
