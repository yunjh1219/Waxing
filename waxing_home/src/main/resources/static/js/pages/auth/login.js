document.getElementById('loginBtn').addEventListener('click', async (event) => {
    event.preventDefault(); // 이게 핵심입니다!

    const userNum = document.getElementById('userNum').value.trim();
    const password = document.getElementById('password').value.trim();

    const loginData = {
        userNum,
        password
    }

    try {
        const response = await fetch('/api/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(loginData)
        });

        if (!response.ok) {
            const errorData = await response.json();
            alert(`로그인 실패: ${errorData.message || '아이디 또는 비밀번호를 확인하세요.'}`);
            return;
        }

        // 여기서 헤더에서 토큰 추출
        const token = response.headers.get('Authorization'); // "Bearer <token>"

        if (token) {
            localStorage.setItem('jwtToken', token.replace('Bearer ', ''));
        } else {
            alert('토큰이 응답 헤더에 없습니다.');
            return;
        }

        const data = await response.json();
        console.log('서버 응답 바디:', data);

        alert('로그인 성공!');
        window.location.href = '/'; // 로그인 성공 시 메인 페이지로 이동
    } catch (error) {
        alert('서버 오류가 발생했습니다.');
        console.error(error);
    }
});