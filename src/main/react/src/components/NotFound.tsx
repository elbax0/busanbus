import {Box, Button, Typography} from "@mui/material";
import {Link} from "react-router-dom";

const NotFound = () => {
    // 몰?루 이미지
    // 돌아가기 버튼, 홈으로 버튼

    return (
        <>
            <Box>
                <Typography variant="h3" align="center">페이지를 찾을 수 없습니다. :(</Typography>
            </Box>
            <Box mt={2}>
                <Link to="/">
                    <Button variant="contained">돌아가기</Button>
                </Link>
            </Box>
        </>
    );
};

export default NotFound;