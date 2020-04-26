import React, { useState } from 'react';
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

const CreatePostForm = (props) => {
  const [title, setTitle] = useState('');
  const [content, setContent] = useState('');
  const [creatorName, setCreatorName] = useState('');

  const submitPost = () => {
    fetch('http://178.128.60.189:8082/api/post', {
      method: 'POST',
      body: {
        content,
        title,
        creatorName,
        createdDate: new Date(),
        mediaURL:
          'https://image.freepik.com/free-vector/infographic-with-details-about-coronavirus-with-illustrated-sick-man_23-2148438094.jpg',
      },
      // mode: 'no-cors',
    })
      // .then((res) => res.json())
      .then((res) => console.log(res));
  };

  return (
    <div className='animated fadeIn'>
      <Row>
        <Col xs='12' md={{ size: 10, offset: 1 }}>
          <Card>
            <CardHeader>
              <div className='text-center text-uppercase font-weight-bold'>
                TẠO BÀI VIẾT
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
                    <Label htmlFor='text-input'>Tiêu đề</Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Input
                      type='textarea'
                      className='form-control-success'
                      id='text-input'
                      name='text-input'
                      placeholder='Text'
                      rows='3'
                      value={title}
                      onChange={(e) => setTitle(e.target.value)}
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
                      value={content}
                      onChange={(e) => setContent(e.target.value)}
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
                    <Label htmlFor='file-multiple-input'>
                      Multiple File input
                    </Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Input
                      type='file'
                      id='file-multiple-input'
                      name='file-multiple-input'
                      multiple
                    />
                  </Col>
                </FormGroup>

                <FormGroup row hidden>
                  <Col md='3'>
                    <Label className='custom-file' htmlFor='custom-file-input'>
                      Custom file input
                    </Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Label className='custom-file'>
                      <Input
                        className='custom-file'
                        type='file'
                        id='custom-file-input'
                        name='file-input'
                      />
                      <span className='custom-file-control'></span>
                    </Label>
                  </Col>
                </FormGroup>

                <FormGroup row>
                  <Col md='3'>
                    <Label htmlFor='text-input'>Người đăng</Label>
                  </Col>
                  <Col xs='12' md='9'>
                    <Input
                      type='text'
                      id='text-input'
                      name='text-input'
                      placeholder='Tên người đăng'
                      value={creatorName}
                      onChange={(e) => setCreatorName(e.target.value)}
                    />
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
                    onClick={submitPost}
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

export default CreatePostForm;
