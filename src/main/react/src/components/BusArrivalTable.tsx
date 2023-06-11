import {CircularProgress, Table, TableBody, TableCell, TableContainer, TableHead, TableRow} from "@mui/material";

type BusArrivalType = {
    lineno: string;
    min1: number;
    station1: number;
}

type BusArrivalTableType = {
    busList: Array<BusArrivalType>;
}

const BusArrivalTable = (props: BusArrivalTableType) => {
    const busList = props.busList;

    return (
        <>
            <TableContainer>
                <Table sx={{ minWidth: 400 }} aria-label="simple table">
                    <TableHead>
                        <TableRow>
                            <TableCell align="center">버스 번호</TableCell>
                            <TableCell align="center">예상 도착 시간</TableCell>
                            <TableCell align="center">위치</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {
                            busList.length == 0 ?
                                <TableRow>
                                    <TableCell colSpan={3} align="center">
                                        <CircularProgress/>
                                    </TableCell>
                                </TableRow>
                                :
                                busList.map((bus) =>
                                    <TableRow key={bus.lineno}>
                                        <TableCell align="center">{bus.lineno}</TableCell>
                                        <TableCell align="center">{bus.min1 === null ? '도착 정보 없음' : bus.min1 == 1 ? '곧 도착' : `${bus.min1}분 후 도착`}</TableCell>
                                        <TableCell align="center">{bus.min1 === null ? '' : bus.min1 == 1 ? '' : `${bus.station1} 정거장 전`}</TableCell>
                                    </TableRow>
                                )
                        }

                    </TableBody>
                </Table>
            </TableContainer>
        </>
    );
};

export default BusArrivalTable;