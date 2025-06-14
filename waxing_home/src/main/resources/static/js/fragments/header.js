
// 스크롤 내릴 때 padding 제거되게 -----------------------------------------------------------------------------
window.addEventListener('scroll', function() {
    const gnb = document.querySelector('.header'); // .gnb 요소 선택
    if (window.scrollY > 0) { // 스크롤이 0보다 클 경우
        gnb.classList.add('scrolled'); // 'scrolled' 클래스 추가
    } else {
        gnb.classList.remove('scrolled'); // 'scrolled' 클래스 제거
    }
});

// overlay -----------------------------------------------------------------------------
function toggleMenu(button) {
    const overlay = document.querySelector('.overlay');
    const isOpened = button.classList.toggle('opened');
    button.setAttribute('aria-expanded', isOpened);

    // overlay의 상태에 따라 클래스 추가 또는 제거
    if (isOpened) {
        overlay.classList.add('open'); // overlay 보이게 설정
    } else {
        overlay.classList.remove('open'); // overlay 숨김
    }
}

// 로그인 상태 유무에 따른 login/회원가입/로그아웃 보여주기
document.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('jwtToken');

    const loginLink = document.getElementById('loginLink');
    const joinLink = document.getElementById('joinLink');
    const logoutBtn = document.getElementById('logoutBtn');

    if (token) {
        // 로그인 상태
        loginLink.style.display = 'none';
        joinLink.style.display = 'none';
        logoutBtn.style.display = 'inline-block';

        logoutBtn.addEventListener('click', () => {
            localStorage.removeItem('jwtToken');
            alert('로그아웃 되었습니다.');
            window.location.reload(); // 새로고침으로 상태 반영
        });
    } else {
        // 로그아웃 상태
        loginLink.style.display = 'inline-block';
        joinLink.style.display = 'inline-block';
        logoutBtn.style.display = 'none';
    }
});