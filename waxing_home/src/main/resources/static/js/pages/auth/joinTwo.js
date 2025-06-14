document.addEventListener('DOMContentLoaded', function () {

    // 전화번호 입력에 숫자만 허용하고 12글자까지만 입력되도록 하는 이벤트 추가
    phone.addEventListener('input', function () {
        // 숫자만 허용
        this.value = this.value.replace(/[^0-9]/g, '');

        // 최대 12글자까지 입력되도록 제한
        if (this.value.length > 12) {
            this.value = this.value.slice(0, 12);
        }
    });

    // userNum 입력값은 영문자와 숫자만 허용
    userNum.addEventListener('input', function () {
        this.value = this.value.replace(/[^a-zA-Z0-9]/g, '');
    });


    document.getElementById("joinBtn").addEventListener('click',
        async (event) => {
            event.preventDefault(); // 폼 기본 제출 막기

            const userNum = document.getElementById('userNum').value.trim();
            const password = document.getElementById('password').value.trim();
            const passwordConfirm = document.getElementById('passwordConfirm').value.trim();
            const name = document.getElementById('name').value.trim();
            const address = document.getElementById('address').value.trim();
            const phone = document.getElementById('phone').value.trim();
            const email = document.getElementById('email').value.trim();

            // 비밀번호 확인 검사
            if (password !== passwordConfirm) {
                alert("비밀번호가 일치하지 않습니다.");
                return;
            }

            // 이메일 형식 검사 (여기 추가)
            const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
            if (!emailRegex.test(email)) {
                alert("이메일 형식이 올바르지 않습니다.");
                return; // 이메일 형식이 올바르지 않으면 제출되지 않음
            }

            // 이름에 한글과 영어만 포함된 경우 체크
            const nameRegex = /^[a-zA-Z가-힣]+$/; // 영어와 한글만 허용하는 정규식
            if (!nameRegex.test(name)) {
                alert("이름의 형식이 올바르지 않습니다.");
                return; // 이름이 올바르지 않으면 제출되지 않음
            }

// 이름이 2글자 이상인지 확인
            if (name.length < 2) {
                alert("이름의 형식이 올바르지 않습니다.");
                return; // 이름이 2글자 미만이면 제출되지 않음
            }


            // 빈 값 검사
            if (!userNum || !password || !name || !address || !phone || !email) {
                alert("모든 항목을 입력해주세요.");
                return;
            }


            const joinData = {
                userNum,
                password,
                name,
                address,
                phone,
                email
            };

            try {
                const response = await fetch('/api/auth/join', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    body: JSON.stringify(joinData)
                });

                if (response.ok) {
                    alert('회원가입이 완료되었습니다.');
                    window.location.href = '/';
                } else {
                    const errorData = await response.json();
                    console.log("서버 응답 에러 데이터:", errorData);
                    alert(`${errorData.message || '회원가입이 실패하였습니다.'}`);
                    return;
                }
            } catch (error) {
                alert('서버 오류가 발생했습니다.');
                console.error(error);
            }
        });
});

