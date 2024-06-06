import React, { useEffect, useState } from 'react';
import { GetCompanyById, UpdateCompany, GetSearchedCompanies, GetAllCompanies } from "../../services/CompanyService";
import { useParams } from 'react-router-dom';
import { GetCompanyAdministratorsByCompanyId } from '../../services/UserService';
import Paper from '@mui/material/Paper';
import Table from '@mui/material/Table';
import TableBody from '@mui/material/TableBody';
import TableCell from '@mui/material/TableCell';
import TableContainer from '@mui/material/TableContainer';
import TableHead from '@mui/material/TableHead';
import TableRow from '@mui/material/TableRow';
import { Button, Rating } from '@mui/material';
import Stack from '@mui/material/Stack';
import TextField from '@mui/material/TextField';
import FormControl from '@mui/material/FormControl';
import InputLabel from '@mui/material/InputLabel';
import Select from '@mui/material/Select';
import MenuItem from '@mui/material/MenuItem';
import Checkbox from '@mui/material/Checkbox';
import ListItemText from '@mui/material/ListItemText';
import Chip from '@mui/material/Chip';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { DatePicker } from '@mui/x-date-pickers/DatePicker';
import { GetEquipmentByCompanyId } from '../../services/EquipmentService';
import { Box } from '@mui/material';
import { CreateReservedDate, GetAllReservedDates, GetTrackingsByEquipmentId } from '../../services/ReservedDateService';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import IconButton from '@mui/material/IconButton';
import MenuIcon from '@mui/icons-material/Menu';
import Typography from '@mui/material/Typography';
import { Link } from 'react-router-dom';
import dayjs from 'dayjs';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';
import Grid from '@mui/material/Grid';
import * as styles from './company-component.css';
import { GetAllPredefinedDates, CreatePredefinedDate, DeletePredefinedDate } from '../../services/PredefinedDateService';
import DeleteIcon from '@mui/icons-material/Delete';
import EquipmentSearchComponent from '../equipmentSearchComponent/equipmentSearchComponent';
import AddIcon from '@mui/icons-material/Add';
import Modal from '@mui/material/Modal';
import { CreateEquipment, UpdateEquipment, DeleteEquipment } from '../../services/EquipmentService';
import EditIcon from '@mui/icons-material/Edit';
import CheckIcon from '@mui/icons-material/Check';
import CancelIcon from '@mui/icons-material/Cancel';
import { findEquipmentByName, GetEquipments } from "../../services/EquipmentService";
import StarIcon from '@mui/icons-material/Star';
import '../equipmentSearchComponent/equipment-search-component.css';
import TrackChangesIcon from '@mui/icons-material/TrackChanges';
import Dialog from '@mui/material/Dialog';
import DialogContent from '@mui/material/DialogContent';
import DialogContentText from '@mui/material/DialogContentText';
import DialogTitle from '@mui/material/DialogTitle';
import ReserveEquipmentComponent from '../reserveEquipmentComponent/reserve-equipment-component';
import MonitorHeartIcon from '@mui/icons-material/MonitorHeart';
import { GetUserByUsername } from "../../services/UserService"
import authService from "../../services/auth.service";
import UserCreateContractComponent from '../userCreateContractComponent/userCreateContractComponent';
import {GetContractByUserId} from '../../services/ContractService';
import TrackingContractComponent from '../trackingContractComponent/trackingContractComponent';

const ITEM_HEIGHT = 48;
const ITEM_PADDING_TOP = 8;
const MenuProps = {
    PaperProps: {
        style: {
            maxHeight: ITEM_HEIGHT * 4.5 + ITEM_PADDING_TOP,
            width: 250,
        },
    },
};

function CompanyComponent() {
    const id = useParams().id;

    const [user, setUser] = useState({})
    const authUser = localStorage.getItem('authUser') ? JSON.parse(localStorage.getItem('authUser')) : null;

    const [companyData, setCompanyData] = useState({
        id: 0,
        name: "",
        address: "",
        description: "",
        avgGrade: 0,
        predefinedDatesId: [],
        administratorId: []
    });

    const [editMode, setEditMode] = useState(false);
    const [companyAdministrators, setCompanyAdministrators] = useState([]);
    const [selectedAdmin, setSelectedAdmin] = useState([]);
    const [equipment, setEquipment] = useState([]);
    const [date, setDate] = useState(null);

    const [predefinedDates, setPredefinedDates] = useState([])
    const [predefinedDatesFromBase, setPredefinedDatesFromBase] = useState([])
    const [duration, setDuration] = useState('');
    const [time, setTime] = useState();
    const [datesToDelete, setDatesToDelete] = useState([])
    const [newEquipment, setNewEquipment] = useState({
        id: 0,
        name: '',
        type: 0,
        grade: '',
        companyId: id,
        description: '',
        quantity: 0
    })

    const [editEquipment, setEditEquipment] = useState({
        id: '',
        name: '',
        type: 0,
        grade: '',
        companyId: id,
        description: '',
        quantity: 0
    })

    const [equipmentIdEdit, setEquipmentIdEdit] = useState(0)
    const [existingReservedDates, setExistingReservedDates] = useState([])

    const [textboxValue, setTextboxValue] = useState('');
    const [ratingValue, setRatingValue] = useState(0);
    const [hover, setHover] = useState(-1);
    const [open, setOpen] = useState(false);
    const [trackingOrders, setTrackingOrders] = useState([]);

    
    useEffect(() => {
        
        const fetchData = async () => {
            try {
                if (!authUser)
                    return;

                const userRes = await GetUserByUsername(authUser?.username);
                const companyRes = await GetCompanyById(id);
                const equipmentRes = await GetEquipmentByCompanyId(id);
                const companyAdminsRes = await GetCompanyAdministratorsByCompanyId(id);
                const predefinedDatesRes = await GetAllPredefinedDates();
                const existingReservedDatesRes = await GetAllReservedDates();

                setUser(userRes.data);
                setCompanyData(companyRes.data);
                setEquipment(equipmentRes.data);
                setCompanyAdministrators(companyAdminsRes.data);
                setExistingReservedDates(existingReservedDatesRes.data);

                const filteredData = predefinedDatesRes.data.filter(d => companyRes.data.administratorId.filter(id => id == d.companyAdminId).length > 0)

                setPredefinedDates(filteredData);
                setPredefinedDatesFromBase(filteredData);
            } catch (error) {
                console.error('Error fetching data:', error);
            }
        };

        fetchData();
    }, [id, companyData.id]);

    function logOut() {
        window.location.href = '/home';
        authService.logout();
    }

    function formatDate(date) {
        const day = String(date.getDate()).padStart(2, '0');
        const month = String(date.getMonth() + 1).padStart(2, '0');
        const year = date.getFullYear();

        const hours = String(date.getHours()).padStart(2, '0');
        const minutes = String(date.getMinutes()).padStart(2, '0');

        return `${day}/${month}/${year} ${hours}:${minutes}`;
    }


    const formatMillisecondsToDate = (milliseconds) => {
        const date = new Date(milliseconds);
        return formatDate(date);
    };

    const handleEditClick = () => {
        setEditMode(true);
    };

    const handleSaveClick = async () => {
        try {
            if (companyData.avgGrade < 1 || companyData.avgGrade > 5) {
                alert("Average grade has to be between 1 and 5!");
                return;
            }

            for (const predefinedDate of predefinedDates) {
                if (!predefinedDatesFromBase.find(date => date.id === predefinedDate.id)) {
                    await CreatePredefinedDate(predefinedDate, id);
                }
            }


            await UpdateCompany(companyData.id, companyData);
            setEditMode(false);

            for (const predefDateId of datesToDelete) {
                if (predefinedDatesFromBase.find(date => date.id === predefDateId))
                    await DeletePredefinedDate(predefDateId, id);
            }

            window.location.reload();

        } catch (error) {
            console.error('Error updating company data or processing predefined dates:', error);
        }
    };

    const handleCancelClick = () => {
        setEditMode(false);

        window.location.reload();
    };

    const handleInputChange = (field, value) => {
        setCompanyData((prevData) => ({
            ...prevData,
            [field]: value,
        }));
    };

    const handleEquipmentInputChange = (field, value) => {
        setNewEquipment((prevData) => ({
            ...prevData,
            [field]: value,
        }))
    }

    const handleAdminChange = (event) => {
        const selectedAdmin = event.target.value;
        setSelectedAdmin(selectedAdmin);
        companyData.administratorsId = selectedAdmin;
    };

    const handleTimeChange = (event) => {
        setTime(event.target.value);
    }
    const handleDurationChange = (event) => {
        setDuration(event.target.value);
    };

    const handleDelete = (id) => {
        const updatedPredefinedDates = predefinedDates.filter(
            (date) => date.id !== id
        );
        setPredefinedDates(updatedPredefinedDates);

        setDatesToDelete([...datesToDelete, id]);
    }

    const handleAddClick = () => {
        if (new Date(date).getTime() < new Date().getTime()) {
            alert("You can't pick date before todays");
            return;
        }

        if (time == null || time.split(':').length == 1) {
            alert("You have to enter staring time in format 08:00!");
            return;
        }

        const newPredefinedDate = {
            id: Math.random(),
            companyAdminId: selectedAdmin,
            dateTimeInMs: new Date(date).getTime() + time.split(':')[0] * 3600000,
            duration: duration
        }

        if (newPredefinedDate.companyAdminId == null || newPredefinedDate.dateTimeInMs == null || newPredefinedDate.duration == '' || newPredefinedDate.duration == null) {
            alert("You have to pick company admin, date and duration to create predefined dates")
            return;
        }
        if (predefinedDates.filter(d => d.companyAdminId == newPredefinedDate.companyAdminId
            && d.dateTimeInMs == newPredefinedDate.dateTimeInMs).length == 0)
            setPredefinedDates([...predefinedDates, newPredefinedDate]);
        else {
            alert("Company admin is already asigned on this date")
        }
    }

    const handleAddEquipmentClick = async () => {
        try {
            const eq = await CreateEquipment(newEquipment);
            console.log(eq)
            setEquipment([...equipment, eq.data])

            setNewEquipment({
                name: '',
                type: 0,
                grade: '',
                companyId: id,
                description: '',
                quantity: 0
            })
        }
        catch (error) {
            console.error(error.message);
        }

    }

    const handleEditEquipmentClick = (equipmentEdit) => {
        setEquipmentIdEdit(equipmentEdit.id)
        setEditEquipment(equipmentEdit)
    }

    const handleCancelEquipmentClick = () => {
        setEquipmentIdEdit(0)
    }

    const handleEditEquipmentInputChange = (field, value) => {
        setEditEquipment((prevData) => {
            const updatedData = {
                ...prevData,
                [field]: value,
            };
            return updatedData;
        });
    }

    const handleUpdateEquipmentClick = () => {
        const updatedEquipment = equipment.map(e => {
            if (e.id === editEquipment.id) {
                return editEquipment;
            }
            return e;
        });

        setEquipment(updatedEquipment);
        setEquipmentIdEdit(0);
        UpdateEquipment(editEquipment.id, editEquipment);
    }

    const handleRemoveEquipmentClick = (id) => {
        const filteredEquipment = equipment.filter(e => e.id != id);

        setEquipment(filteredEquipment);
        DeleteEquipment(id);
    }

    const equipmentTypeToString = (type) => {
        switch (type) {
            case 0:
                return "DIAGNOSTIC_EQUIPMENT"
            case 1:
                return "DURABLE_MEDICAL_EQUIPMENT"
            case 2:
                return "TREATMENT_EQUIPMENT"
            case 3:
                return "LIFE_SUPPORT_EQUIPMENT"
            default: return type
        }
    }

    const equipmentTypeToInt = (type) => {
        switch (type) {
            case "DIAGNOSTIC_EQUIPMENT":
                return 0
            case "DURABLE_MEDICAL_EQUIPMENT":
                return 1
            case "TREATMENT_EQUIPMENT":
                return 2
            case "LIFE_SUPPORT_EQUIPMENT":
                return 3
            default: return type
        }
    }

    const handleTextBoxChange = (event) => {
        setTextboxValue(event.target.value);
    };
    const handleSearch = () => {
        findEquipmentByName(textboxValue, ratingValue)
            .then((res) => {
                const filteredRes = res.data.filter(e => e.companyId == id);
                setEquipment(filteredRes);
            })
            .catch((error) => console.error('Error fetching equipments data:', error));

    }
    const handleReset = () => {
        GetEquipmentByCompanyId(id)
            .then((res) => {
                setEquipment(res.data);
                setTextboxValue("");
                setRatingValue(0);
            })
            .catch((error) => console.error('Error fetching equipments data:', error));

    }
    function getLabelText(value) {
        return `${value} Star${value !== 1 ? 's' : ''}, ${labels[value]}`;
    }
    const labels = {
        0.5: 'Useless',
        1: 'Useless+',
        1.5: 'Poor',
        2: 'Poor+',
        2.5: 'Ok',
        3: 'Ok+',
        3.5: 'Good',
        4: 'Good+',
        4.5: 'Excellent',
        5: 'Excellent+',
    };

    const handleClickOpen = async (equipmentId) => {
        try {
            const orders = await GetTrackingsByEquipmentId(equipmentId);

            setTrackingOrders(orders.data);
            console.log(orders.data)
            setOpen(true);
        } catch (error) {
            console.error(error.message);
        }

    };

    const handleClose = () => {
        setOpen(false);
    };

    return (
        <>
            {authUser ? (
                <>
                    <Box sx={{ flexGrow: 1 }}>
                        <AppBar position="static" color='secondary'>
                            <Toolbar>
                                <IconButton
                                    size="large"
                                    edge="start"
                                    color="accent"
                                    aria-label="menu"
                                    sx={{ mr: 2 }}
                                >
                                    <MenuIcon />
                                </IconButton>
                                <Typography variant="h6" color="accent" component="div" sx={{ flexGrow: 1 }}>
                                    <span style={{ fontWeight: 'bold' }}>MediConnect</span>
                                </Typography>
                                <Button color="accent" component={Link} to="/home">Home</Button>

                                {authUser ?
                                    <>
                                        {user?.role === 'ROLL_COMPANY_ADMIN' ?
                                            <Button color="accent" component={Link} to="/companyAdmin">Profile</Button>
                                            : <></>
                                        }
                                        {user?.role === 'ROLL_USER' ?
                                            <Button color="accent" component={Link} to="/user/page">Profile</Button>
                                            : <></>
                                        }
                                        <Button color="accent" component={Link} to="/companies">Companies</Button>
                                        <Button color="accent" component={Link} onClick={logOut}>Logout</Button>
                                       
                                    </>
                                    : <><Button color="accent" component={Link} to="/login">Login</Button>
                                        <Button color="accent" component={Link} to="/register">Register</Button>
                                    </>}

                            </Toolbar>
                        </AppBar>
                    </Box>
                    <div style={{ display: 'flex', justifyContent: 'center', alignItems: 'center', marginTop: '10vh' }}>
                        
                        <Stack spacing={2} direction="column">
                            <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', width: '90vw' }}>
                                <TableContainer component={Paper} sx={{ maxWidth: '80vw', margin: 'auto' }}>
                                    <Table>
                                        <TableHead>
                                            <TableRow>
                                                <TableCell>Name</TableCell>
                                                <TableCell>Address</TableCell>
                                                <TableCell>Description</TableCell>
                                                <TableCell>Average Grade</TableCell>
                                            </TableRow>
                                        </TableHead>
                                        <TableBody>
                                            <TableRow>
                                                {editMode ? (
                                                    <>
                                                        <TableCell>
                                                            <TextField
                                                                value={companyData.name}
                                                                onChange={(e) => handleInputChange('name', e.target.value)}
                                                            />
                                                        </TableCell>
                                                        <TableCell>
                                                            <TextField
                                                                value={companyData.address}
                                                                onChange={(e) => handleInputChange('address', e.target.value)}
                                                            />
                                                        </TableCell>
                                                        <TableCell>
                                                            <TextField
                                                                value={companyData.description}
                                                                onChange={(e) => handleInputChange('description', e.target.value)}
                                                            />
                                                        </TableCell>
                                                        <TableCell>
                                                            <TextField
                                                                value={companyData.avgGrade}
                                                                onChange={(e) => handleInputChange('avgGrade', e.target.value)}
                                                            />
                                                        </TableCell>
                                                    </>
                                                ) : (
                                                    <>
                                                        <TableCell>{companyData.name}</TableCell>
                                                        <TableCell>{companyData.address}</TableCell>
                                                        <TableCell>{companyData.description}</TableCell>
                                                        <TableCell>{companyData.avgGrade}</TableCell>
                                                    </>
                                                )}
                                            </TableRow>
                                        </TableBody>
                                    </Table>
                                </TableContainer>

                                {editMode ? (
                                    <>
                                        <Box
                                            borderRadius={10}  // Set the border radius
                                            padding={5}
                                            sx={{
                                                margin: 'auto',
                                                width: '80vw'
                                            }}>
                                            <Box
                                                sx={{

                                                    display: 'flex',
                                                    flexDirection: 'column',
                                                    marginLeft: '100px',
                                                    marginRight: '100px',

                                                }}
                                            >
                                                <Box
                                                    sx={{
                                                        display: 'flex',
                                                        alignItems: 'center',
                                                        justifyContent: 'center',
                                                        flexDirection: 'column',
                                                        marginBottom: '10px'
                                                    }}>
                                                    <TextField
                                                        id="outlined-basic"
                                                        label="Search"
                                                        variant="outlined"
                                                        color='secondary'
                                                        value={textboxValue}
                                                        onChange={handleTextBoxChange}
                                                        fullWidth
                                                        margin='normal'
                                                        focused />
                                                    <label className='labelClass'>Rating: </label>
                                                    <Box sx={{
                                                        display: 'flex',
                                                        alignItems: 'center',
                                                        flexDirection: 'row',
                                                        width: '200px',
                                                        margin: 'normal',
                                                        justifyContent: 'space-between'
                                                    }}>

                                                        <Rating
                                                            name="hover-feedback"
                                                            value={ratingValue}
                                                            precision={0.5}
                                                            getLabelText={getLabelText}
                                                            onChange={(event, newValue) => {
                                                                setRatingValue(newValue);
                                                            }}
                                                            onChangeActive={(event, newHover) => {
                                                                setHover(newHover);
                                                            }}
                                                            size='large'
                                                            emptyIcon={<StarIcon style={{ opacity: 0.55 }} fontSize="inherit" />}
                                                        />

                                                        {ratingValue !== null && (
                                                            <Box sx={{ ml: 2, color: 'secondary' }}>{labels[hover !== -1 ? hover : ratingValue]}</Box>
                                                        )}
                                                    </Box>
                                                </Box>

                                                <Button variant="contained" onClick={handleSearch} className='button-add'>Search</Button>
                                                <br></br>
                                                <Button variant="contained" onClick={handleReset} className='button-cancel' sx={{ color: '#c5ab85' }}>Reset</Button>

                                            </Box>
                                        </Box>

                                    </>) : (<></>)}
                                <Box sx={{ width: '80vw', margin: 'auto', mt: 5, bgcolor: 'background.paper' }}>
                                    <TableContainer component={Paper} sx={{ maxWidth: '100%' }}>
                                        <Table>
                                            <TableHead>
                                                <TableRow>
                                                    <TableCell>Equipment name</TableCell>
                                                    <TableCell>Type</TableCell>
                                                    <TableCell>Grade</TableCell>
                                                    {editMode ? (
                                                        <>
                                                            <TableCell>Description</TableCell>
                                                            <TableCell>Quantity</TableCell>
                                                            <TableCell></TableCell>
                                                            <TableCell></TableCell>
                                                            <TableCell></TableCell>
                                                        </>
                                                    ) : (<></>)}
                                                </TableRow>
                                            </TableHead>
                                            <TableBody>
                                                {equipment.map((equipmentItem) => (
                                                    <TableRow key={equipmentItem.id}>
                                                        {equipmentItem.id === equipmentIdEdit ? (
                                                            <>
                                                                <TableCell>
                                                                    <TextField
                                                                        value={editEquipment.name}
                                                                        onChange={(e) => handleEditEquipmentInputChange('name', e.target.value)}
                                                                    /></TableCell>
                                                                <TableCell>
                                                                    <FormControl fullWidth>
                                                                        <InputLabel id="demo-multiple-checkbox-label">Equipment type</InputLabel>
                                                                        <Select
                                                                            labelId="demo-simple-select-label"
                                                                            id="demo-simple-select"
                                                                            value={equipmentTypeToInt(editEquipment.type)}
                                                                            label="Equipment type"
                                                                            onChange={(e) => handleEditEquipmentInputChange('type', e.target.value)}
                                                                        >
                                                                            <MenuItem value={0}>DIAGNOSTIC_EQUIPMENT</MenuItem>
                                                                            <MenuItem value={1}>DURABLE_MEDICAL_EQUIPMENT</MenuItem>
                                                                            <MenuItem value={2}>TREATMENT_EQUIPMENT</MenuItem>
                                                                            <MenuItem value={3}>LIFE_SUPPORT_EQUIPMENT</MenuItem>
                                                                        </Select>
                                                                    </FormControl>
                                                                </TableCell>
                                                                <TableCell><TextField
                                                                    value={editEquipment.grade}
                                                                    onChange={(e) => handleEditEquipmentInputChange('grade', e.target.value)}
                                                                /></TableCell>
                                                                {editMode ? (
                                                                    <>
                                                                        <TableCell><TextField
                                                                            value={editEquipment.description}
                                                                            onChange={(e) => handleEditEquipmentInputChange('description', e.target.value)}
                                                                        /></TableCell>
                                                                        <TableCell><TextField
                                                                            value={editEquipment.quantity}
                                                                            onChange={(e) => handleEditEquipmentInputChange('quantity', e.target.value)}
                                                                        /></TableCell>
                                                                        <TableCell><CancelIcon className='button-remove-equipment' onClick={handleCancelEquipmentClick} /></TableCell>
                                                                        <TableCell><CheckIcon className='button-remove-equipment' onClick={handleUpdateEquipmentClick} /></TableCell>
                                                                    </>
                                                                ) : (<></>)}
                                                            </>
                                                        ) : (
                                                            <>
                                                                <TableCell>{equipmentItem.name}</TableCell>
                                                                <TableCell>{equipmentTypeToString(equipmentItem.type)}</TableCell>
                                                                <TableCell>{equipmentItem.grade}</TableCell>
                                                                {editMode ? (
                                                                    <>
                                                                        <TableCell>{equipmentItem.description}</TableCell>
                                                                        <TableCell>{equipmentItem.quantity}</TableCell>
                                                                        <TableCell><EditIcon className='button-remove-equipment' onClick={() => handleEditEquipmentClick(equipmentItem)} /></TableCell>
                                                                        {existingReservedDates.findIndex(date => !date.pickedUp && date.equipments.includes(equipmentItem.id)) === -1 ? (
                                                                            <>
                                                                                <TableCell><DeleteIcon className='button-remove-equipment' onClick={() => handleRemoveEquipmentClick(equipmentItem.id)}
                                                                                /></TableCell>
                                                                            </>
                                                                        ) : (
                                                                            <>
                                                                                <TableCell></TableCell>
                                                                            </>
                                                                        )}
                                                                        <TableCell><TrackChangesIcon className='button-remove-equipment' onClick={() => handleClickOpen(equipmentItem.id)} /></TableCell>
                                                                    </>
                                                                ) : (<></>)}
                                                            </>
                                                        )}


                                                    </TableRow>
                                                ))}

                                                <Dialog open={open} onClose={handleClose} maxWidth='lg' fullWidth >
                                                    <DialogTitle>Tracking orders:</DialogTitle>
                                                    <DialogContent>
                                                        <DialogContentText>
                                                            {trackingOrders.map(order => (
                                                                <>
                                                                    <div key={order.orderId}>
                                                                        {formatMillisecondsToDate(order.dateTimeInMs)}<br />
                                                                        Buyer: {order.userName}<br />
                                                                        Delivering: {order.companyAdminName}<br />
                                                                        {order.pickedUp ? (
                                                                            <>Picked up: Yes</>
                                                                        ) : (
                                                                            <>Picked up: No</>
                                                                        )}
                                                                    </div>
                                                                    <hr></hr>
                                                                </>
                                                            ))}
                                                        </DialogContentText>
                                                    </DialogContent>
                                                </Dialog>
                                                {editMode ? (
                                                    <>
                                                        <TableRow>
                                                            <TableCell><TextField
                                                                value={newEquipment.name}
                                                                onChange={(e) => handleEquipmentInputChange('name', e.target.value)}
                                                            />
                                                            </TableCell>
                                                            <TableCell>
                                                                <FormControl fullWidth>
                                                                    <InputLabel id="demo-multiple-checkbox-label">Equipment type</InputLabel>
                                                                    <Select
                                                                        labelId="demo-simple-select-label"
                                                                        id="demo-simple-select"
                                                                        value={newEquipment.type}
                                                                        label="Equipment type"
                                                                        onChange={(e) => handleEquipmentInputChange('type', e.target.value)}
                                                                    >
                                                                        <MenuItem value={0}>DIAGNOSTIC_EQUIPMENT</MenuItem>
                                                                        <MenuItem value={1}>DURABLE_MEDICAL_EQUIPMENT</MenuItem>
                                                                        <MenuItem value={2}>TREATMENT_EQUIPMENT</MenuItem>
                                                                        <MenuItem value={3}>LIFE_SUPPORT_EQUIPMENT</MenuItem>
                                                                    </Select>
                                                                </FormControl>
                                                            </TableCell>
                                                            <TableCell><TextField
                                                                value={newEquipment.grade}
                                                                onChange={(e) => handleEquipmentInputChange('grade', e.target.value)}
                                                            /></TableCell>
                                                            <TableCell>
                                                                <TextField
                                                                    value={newEquipment.description}
                                                                    onChange={(e) => handleEquipmentInputChange('description', e.target.value)}
                                                                />
                                                            </TableCell>
                                                            <TableCell>
                                                                <TextField
                                                                    value={newEquipment.quantity}
                                                                    onChange={(e) => handleEquipmentInputChange('quantity', e.target.value)}
                                                                />
                                                            </TableCell>
                                                            <TableCell><AddIcon className='button-remove-equipment' onClick={handleAddEquipmentClick} /></TableCell>
                                                        </TableRow>
                                                    </>
                                                ) : (<></>)}
                                            </TableBody>
                                        </Table>
                                    </TableContainer>
                                </Box>

                                {user.role != "ROLL_COMPANY_ADMIN" ? (
                                    <div>
                                        <p>Datumi</p>
                                    </div>
                                ) : (
                                    <div></div>
                                )}

                                {editMode ? (
                                    <>
                                        <div style={{ width: '80vw' }}>
                                            <Grid container spacing={2} >
                                                <Grid item xs={8}>
                                                    <div style={{ display: 'flex', flexDirection: 'column', height: '100%' }} className='justify-content-start'>
                                                        <FormControl fullWidth sx={{ mt: 5, pb: 2, mb: 2, flex: '1' }}>
                                                            <InputLabel id="demo-multiple-checkbox-label">Administrators</InputLabel>
                                                            <Select
                                                                labelId="demo-multiple-checkbox-label"
                                                                id="demo-multiple-checkbox"
                                                                label="Administrators"
                                                                value={selectedAdmin}
                                                                onChange={handleAdminChange}
                                                                MenuProps={MenuProps}
                                                                sx={{ bgcolor: 'white' }}
                                                            >
                                                                {companyAdministrators.map((admin) => (
                                                                    <MenuItem key={admin.id} value={admin.id}>
                                                                        <ListItemText primary={`${admin.first_name} ${admin.last_name}`} />
                                                                    </MenuItem>
                                                                ))}
                                                            </Select>

                                                            <div className='mt-3'>
                                                                {predefinedDates.map((predefinedDate) => (
                                                                    <Chip
                                                                        key={predefinedDate.id}
                                                                        className='calendar-wrapper mt-2'
                                                                        label={`Admin: ${predefinedDate.companyAdminId}, Date: ${formatMillisecondsToDate(predefinedDate.dateTimeInMs)}, Duration: ${predefinedDate.duration} min`}
                                                                        onDelete={() => handleDelete(predefinedDate.id)}
                                                                    />
                                                                ))}
                                                            </div>
                                                        </FormControl>

                                                    </div>
                                                </Grid>
                                                <Grid item xs={4} className='d-flex flex-column'>
                                                    <div className="calendar-wrapper d-flex justify-content-center p-2 mt-2">
                                                        <LocalizationProvider dateAdapter={AdapterDayjs} >
                                                            <DemoContainer components={['DateCalendar', 'DateCalendar']}>
                                                                <DemoItem label="Pick a date">
                                                                    <DateCalendar value={date} onChange={(newDate) => setDate(newDate)} />
                                                                </DemoItem>
                                                            </DemoContainer>
                                                        </LocalizationProvider>
                                                    </div>
                                                    <Box
                                                        component="form"
                                                        sx={{ '& > :not(style)': { m: 1, width: '100%' }, }}
                                                        noValidate
                                                        autoComplete="off"
                                                    >
                                                        <TextField
                                                            id="outlined-basic-time"
                                                            label="Starting time"
                                                            placeholder='08:00'
                                                            variant="outlined"
                                                            value={time}
                                                            onChange={handleTimeChange}
                                                        />
                                                        <TextField
                                                            id="outlined-basic"
                                                            label="Duration"
                                                            variant="outlined"
                                                            value={duration}
                                                            onChange={handleDurationChange}
                                                        />

                                                    </Box>
                                                </Grid>
                                                <Button variant="contained" className='button-add mb-3' onClick={handleAddClick}>
                                                    Add
                                                </Button>
                                            </Grid>
                                        </div>

                                    </>
                                ) : (<div></div>)}
                            </div>

                            {user.role == 'ROLL_COMPANY_ADMIN' ? (
                                <div style={{ width: '80vw', margin: 'auto' }}>
                                    {editMode ? (
                                        <Stack spacing={2} direction="row">
                                            <Button variant="contained" onClick={handleSaveClick} className='button-wrapper mt-5' color="secondary">
                                                Save
                                            </Button>
                                            <Button variant="outlined" onClick={handleCancelClick} className='button-cancel mt-5' color="secondary">
                                                Cancel
                                            </Button>
                                        </Stack>
                                    ) : (
                                        <>
                                            {companyData.administratorId.filter(adminId => adminId == user.id).length == 1 ?
                                                <Button variant="contained" onClick={handleEditClick} className='button-wrapper mt-5' color="secondary">
                                                    Edit
                                                </Button>
                                                : <></>}
                                        </>
                                    )}
                                    <TrackingContractComponent companyId={id}/>
                                </div>
                            ) : (
                                <div></div>
                            )}

                            {user.role == 'ROLL_USER' ? (
                                <div style={{ width: '80vw', margin: 'auto' }}>
                                    <ReserveEquipmentComponent companyId={id} />
                                    <UserCreateContractComponent companyId={id}/>
                                </div>
                            ) : (
                                <div></div>
                            )}
                        </Stack>
                    </div>
                </>)
                : (<h2>Page not found :/</h2>)}
        </>
    );
}

export default CompanyComponent;    
