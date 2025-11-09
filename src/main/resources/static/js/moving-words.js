// moving-words.js

(function () {
    function createMovingWords(containerSelector = '#movingWords') {
        const reduceMotion = window.matchMedia('(prefers-reduced-motion: reduce)').matches;
        if (reduceMotion) return null;

        const container = document.querySelector(containerSelector);
        if (!container) return null;

        const lines = Array.from(container.querySelectorAll('.line')).map(line => {
            const speed = Number(line.dataset.speed) || 24;
            const direction = (line.dataset.direction === 'right') ? 1 : -1;
            const track = document.createElement('div');
            track.className = 'track';
            const text = line.textContent.trim();
            const spanA = document.createElement('span');
            spanA.textContent = text;
            const spanB = document.createElement('span');
            spanB.textContent = text;
            track.appendChild(spanA);
            track.appendChild(spanB);
            line.textContent = '';
            line.appendChild(track);
            return { lineEl: line, trackEl: track, speed, direction, width: 0, offset: 0 };
        });

        function measure() {
            const vw = Math.max(document.documentElement.clientWidth || 0, window.innerWidth || 0);
            lines.forEach(item => {
                item.trackEl.style.transform = 'translateX(0)';
                if (item.trackEl.scrollWidth < vw * 2) {
                    while (item.trackEl.scrollWidth < vw * 2) {
                        const clone = item.trackEl.children[0].cloneNode(true);
                        item.trackEl.appendChild(clone);
                    }
                }
                item.width = item.trackEl.scrollWidth;
                item.offset = 0;
                item.trackEl.style.transform = `translateX(${item.offset}px)`;
            });
        }

        let lastTs = null;
        let rafId = null;

        function tick(ts) {
            if (!lastTs) lastTs = ts;
            const dt = (ts - lastTs) / 1000;
            lastTs = ts;
            lines.forEach(item => {
                const delta = item.speed * dt * (item.direction === -1 ? -1 : 1);
                item.offset += delta;
                const half = item.width / 2;
                if (item.direction === -1) {
                    if (item.offset <= -half) item.offset += half;
                } else {
                    if (item.offset >= 0) item.offset -= half;
                }
                item.trackEl.style.transform = `translate3d(${item.offset}px,0,0)`;
            });
            rafId = requestAnimationFrame(tick);
        }

        function start() {
            if (rafId) return;
            lastTs = null;
            rafId = requestAnimationFrame(tick);
        }
        function stop() {
            if (!rafId) return;
            cancelAnimationFrame(rafId);
            rafId = null;
        }

        document.addEventListener('visibilitychange', () => {
            if (document.hidden) stop(); else start();
        });

        window.addEventListener('resize', () => {
            measure();
        });

        // init
        measure();
        start();

        // return control object for debugging or manual control
        return { measure, start, stop, lines };
    }

    document.addEventListener('DOMContentLoaded', () => {
        const ctrl = createMovingWords();
        window._movingWords = ctrl;
    });
})();
