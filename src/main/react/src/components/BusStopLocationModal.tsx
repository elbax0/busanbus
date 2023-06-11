import {useState} from "react";
import {Button, Dialog, DialogActions, DialogContent, DialogTitle, Divider, IconButton} from "@mui/material";
import {Close} from "@mui/icons-material";

type BusStopLocationModalType = {
    busStopName: string;
    busStopId: number;
}

const BusStopLocationModal = (props: BusStopLocationModalType) => {
    const [open, setOpen] = useState(false);
    const [zoomLevel, setZoomLevel] = useState(16);
    const busStopName = props.busStopName;
    const busStopId = props.busStopId;

    const handleClickOpen = () => {
        setOpen(true);
    };
    const handleClose = () => {
        setOpen(false);
    };

    const zoomIn = () => {
        setZoomLevel(() => zoomLevel + 1);

    }

    const zoomOut = () => {
        setZoomLevel(() => zoomLevel - 1);
    }

    return (
        <>
            <Button onClick={handleClickOpen}>정류장 위치</Button>
            <Dialog open={open}>
                <DialogTitle sx={{ m: 0, p: 2}} aria-labelledby="customized-dialog-title">
                    {busStopName} - {busStopId}
                    <IconButton aria-label="close" onClick={handleClose} sx={{position: 'absolute', right: 8, top: 8}}>
                        <Close/>
                    </IconButton>
                </DialogTitle>
                <Divider/>

                <DialogContent>
                    <img src={`https://bbus.cc/api/busStop/${busStopId}/map/${zoomLevel}`} />
                    <DialogActions>
                        <Button
                            variant="contained"
                            onClick={zoomIn} {...(zoomLevel === 20) ? { disabled: true } : {}}>지도 확대</Button>
                        <Button
                            variant="contained"
                            onClick={zoomOut} {...(zoomLevel === 1) ? {disabled: true} : {}}>지도 축소</Button>
                    </DialogActions>
                </DialogContent>
            </Dialog>
        </>
    );
};

export default BusStopLocationModal;