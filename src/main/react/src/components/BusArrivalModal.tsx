import {useEffect, useState} from "react";
import {Button, Dialog, DialogContent, DialogTitle, IconButton} from "@mui/material";
import {Close} from "@mui/icons-material";
import BusArrivalTable from "./BusArrivalTable.tsx";

type BusArrivalModalType = {
    busStopName: string;
    busStopId: number;
};

const BusArrivalModal = (props: BusArrivalModalType) => {
    const [open, setOpen] = useState(false);
    const [busList, setBusList] = useState([]);
    const { busStopName, busStopId } = props;

    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    useEffect(() => {
        let eventSource: EventSource;
        if (open) {
            // eventSource = new EventSource(`http://localhost:8080/api/busStop/${busStopId}/arrival/sse`);
            eventSource = new EventSource(`https://bbus.cc/api/busStop/${busStopId}/arrival/sse`);
            eventSource.addEventListener('open', (e) => {
                console.log(e);
                console.log('connected');
            })
            eventSource.addEventListener('message', (e) => {
                const busArrivalData = JSON.parse(e.data);
                console.log(busArrivalData);
                setBusList(busArrivalData.busList);
            });

        }
        return () => {
            if (eventSource) {
                eventSource.close();
            }
        };
    }, [open]);


    return (
        <>
            <Button onClick={handleClickOpen}>버스 도착 정보</Button>
            <Dialog open={open}>
                <DialogTitle sx={{ m: 0, p: 2}} aria-labelledby="customized-dialog-title">
                    {busStopName} - {busStopId}
                    <IconButton aria-label="close" onClick={handleClose} sx={{position: 'absolute', right: 8, top: 8}}>
                        <Close/>
                    </IconButton>
                </DialogTitle>

                <DialogContent>
                    <BusArrivalTable busList={busList}/>
                </DialogContent>
            </Dialog>
        </>
    );
};

export default BusArrivalModal;