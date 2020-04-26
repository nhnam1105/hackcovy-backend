import React from 'react';
import {
  Button,
  Card,
  CardBody,
  CardFooter,
  CardHeader,
  Col,
  Form,
  FormGroup,
  FormFeedback,
  Input,
  Label,
  Row,
} from 'reactstrap';

const CreateRegulationForm = (props) => {
  return (
    <div className='animated fadeIn'>
      <Row>
        <Col xs='12' md={{ size: 10, offset: 1 }}>
          <Card>
            <CardHeader>
              <div className='text-center text-uppercase font-weight-bold'>
                TẠO NỘI QUY
              </div>
            </CardHeader>
            <CardBody>
              <Form
                action=''
                method='post'
                encType='multipart/form-data'
                className='form-horizontal '
              >
                <FormGroup row>
                  <Col md='3'>
                    <Label htmlFor='text-input'>Thể loại</Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Input
                      type='textarea'
                      className='form-control-success'
                      id='text-input'
                      name='text-input'
                      placeholder='Text'
                      rows='3'
                    />
                    <FormFeedback valid>Non-required</FormFeedback>
                  </Col>
                </FormGroup>

                <FormGroup row>
                  <Col md='3'>
                    <Label htmlFor='textarea-input'>Nội dung</Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Input
                      type='textarea'
                      className='form-control-warning'
                      name='textarea-input'
                      id='textarea-input'
                      rows='9'
                      placeholder='Nội dung...'
                      required
                    />
                    <FormFeedback className='help-block'>
                      Please provide a valid information
                    </FormFeedback>
                    <FormFeedback valid className='help-block'>
                      Input provided
                    </FormFeedback>
                  </Col>
                </FormGroup>

                <FormGroup row>
                  <Col md='3'>
                    <Label htmlFor='text-input'>Thứ tự hiển thị </Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Input type='number' id='text-input' name='text-input' />
                  </Col>
                </FormGroup>
              </Form>
            </CardBody>
            <CardFooter>
              <Row>
                <Col xs='6' md='2'>
                  <Button
                    className='btn-outline'
                    type='submit'
                    size='md'
                    color='success'
                  >
                    <i className='fa fa-dot-circle-o'></i> Submit
                  </Button>
                </Col>
                <Col xs='6' md='2'>
                  <Button
                    className='btn-outline'
                    type='reset'
                    size='md'
                    color='danger'
                  >
                    <i className='fa fa-ban'></i> Reset
                  </Button>
                </Col>
              </Row>
            </CardFooter>
          </Card>
        </Col>
      </Row>
    </div>
  );
};

export default CreateRegulationForm;
